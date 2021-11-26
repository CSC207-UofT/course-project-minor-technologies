package com.minortechnologies.workr_backend.networkhandler;

import com.minortechnologies.workr_backend.controllers.dataprocessing.DataFormat;
import com.minortechnologies.workr_backend.controllers.localcache.LocalCache;
import com.minortechnologies.workr_backend.controllers.search.Search;
import com.minortechnologies.workr_backend.controllers.usermanagement.UserManagement;
import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.CustomJobListing;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.listing.ListingType;
import com.minortechnologies.workr_backend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.usecase.factories.EntryDataMapTypeCaster;
import com.minortechnologies.workr_backend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.tomcat.jni.Local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ListingRequestHandler {

    public static HashMap<String, Object> getListing(String uuid){
        LocalCache lc = Application.getLocalCache();
        Entry listing = lc.getListingFromUUID(uuid);
        if (listing == null){
            return null;
        }
        return listing.serialize();
    }

    public static ArrayList<HashMap<String, Object>> getListing(String[] uuids){
        ArrayList<HashMap<String, Object>> listings = new ArrayList<>();
        for (String uuid:
             uuids) {
            HashMap<String, Object> entry = getListing(uuid);
            if (entry != null){
                listings.add(entry);
            }
        }
        return listings;
    }

    public static ArrayList<HashMap<String, Object>> searchListings(HashMap<String, Object> searchQuery){
        try {
            Entry query = ICreateEntry.createEntry(searchQuery);
            if (query instanceof SearchQuery){
                ArrayList<Entry> initResults = Search.searchWeb((SearchQuery) query);
                ArrayList<HashMap<String, Object>> results = new ArrayList<>();
                for (Entry entry:
                     initResults) {
                    results.add(entry.serialize());
                }
                return results;
            }
        } catch (MalformedDataException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static String createListing(String token, String login, Map<String, Object> payload) {
        User user = UserRequestHandler.authenticateAndGetUser(login, token);
        if (user == null){
            return NetworkResponseConstants.TOKEN_AUTH_FAIL_STRING;
        }

        if (payload.containsKey(JobListing.LISTING_TYPE)){
            Object listingType = payload.get(JobListing.LISTING_TYPE);
            if (!(listingType instanceof String || listingType instanceof JobListing)){
                return NetworkResponseConstants.DATA_MALFORMED_STRING;
            }
            payload.put(JobListing.LISTING_TYPE, ListingType.CUSTOM);
        }

        payload.put(JobListing.UID, null);

        try {
            Entry entry = DataFormat.createEntry(payload);
            if (entry instanceof JobListing){
                ((JobListing) entry).setSaved(true);
                LocalCache lc = Application.getLocalCache();
                Entry existing = lc.addJobListing(entry);

                String listingUUID;
                listingUUID = ((JobListing) Objects.requireNonNullElse(existing, entry)).getUUID();

                user.addListingToWatch(listingUUID);
                return listingUUID;
            }
        } catch (MalformedDataException e) {
            e.printStackTrace();
            System.out.println("reachedCatch");
        }
        return NetworkResponseConstants.DATA_MALFORMED_STRING;
    }

    public static int updateListing(String token, String login, Map<String, Object> payload) {
        User user = UserRequestHandler.authenticateAndGetUser(login, token);
        if (user == null){
            return NetworkResponseConstants.TOKEN_AUTH_FAIL;
        }

        String uuid = (String) payload.get(JobListing.UID);
        payload.put(JobListing.LISTING_TYPE, ListingType.CUSTOM);

        HashMap<String, Object> dataCopy = SerializationUtils.clone((HashMap<String, Object>)payload);

        (new EntryDataMapTypeCaster()).convertValueTypes(dataCopy);

        int count = EntryDataMapTypeCaster.malformedDataCount(dataCopy, payload);

        if (count == dataCopy.keySet().size()){
            return NetworkResponseConstants.DATA_MALFORMED;
        }

        LocalCache lc = Application.getLocalCache();
        Entry entry = lc.getListingFromUUID(uuid);
        if (entry == null || entry.getData(JobListing.LISTING_TYPE) != ListingType.CUSTOM){
            return NetworkResponseConstants.DATA_MALFORMED;
        }

        entry.updateEntry(dataCopy);

        return 1;
    }
}
