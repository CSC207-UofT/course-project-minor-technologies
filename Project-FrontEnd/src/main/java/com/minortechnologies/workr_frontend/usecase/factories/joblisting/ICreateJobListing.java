package com.minortechnologies.workr_frontend.usecase.factories.joblisting;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.listing.ListingType;
import com.minortechnologies.workr_frontend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_frontend.usecase.fileio.MalformedDataException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Usecase Factory Interface for creating a specific listing type.
 *
 * subclasses of this must implement verifyJsonIntegrity and also call this interface's
 * implementation of verifyJSONIntegrity as it checks for keys required by all listing types.
 *
 *
 */
public interface ICreateJobListing extends ICreateEntry {
    JobListing create(Map<String, Object> listingDataMap) throws MalformedDataException;

    /**
     * Factory method to create a listing.
     *
     * @param listingMapData - a Map containing the listing data
     * @return A listing created based on the listingMapData
     */
    static JobListing createListing(Map<String, Object> listingMapData) throws MalformedDataException {
        ArrayList<String> missingKeys = verifyMapIntegrityStatic(listingMapData);
        if (missingKeys.size() == 0){
            ListingType type;
            if (listingMapData.get("listingType") instanceof ListingType){
                type = (ListingType) listingMapData.get("listingType");
            }
            else{
                type = ListingType.valueOf((String) listingMapData.get("listingType"));
            }
            switch(type) {
                case CUSTOM:
                    return new CreateCustomJobListing().create(listingMapData);
                case DEMO_SOURCE:
                    return new CreateDemoSourceJobListing().create(listingMapData);
                case LINKED_IN:
                case INDEED:
                    throw new java.lang.UnsupportedOperationException();
                default:
                    throw new MalformedDataException();
            }
        }

        throw new MalformedDataException(ICreateEntry.missingKeyInfo(missingKeys, "JOB LISTING"));
    }

    /**
     * Verifies Data integrity by checking that all required keys are present.
     *
     * @param listingDataMap the map containing the Data for the Listing
     * @return An Arraylist containing any keys missing from the Map. This list is empty if there are no missing keys.
     */
    static ArrayList<String> verifyMapIntegrityStatic(Map<String, Object> listingDataMap) {
        String[] integrity = ("uuid listingType title location pay jobType qualifications requirements " +
                "applicationReq description listingDate crossPlatformDuplicates").split(" ");
        Set<String> keys = listingDataMap.keySet();

        ArrayList<String> missingKeys = new ArrayList<>();
        for (String key : integrity) {
            if (!keys.contains(key)) {
                missingKeys.add(key);
            }
        }
        return missingKeys;
    }
}
