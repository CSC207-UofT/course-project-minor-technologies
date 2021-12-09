package com.minortechnologies.workr_backend.demo.demosource;

import com.minortechnologies.workr_backend.controllers.dataprocessing.DataFormat;
import com.minortechnologies.workr_backend.controllers.search.Search;
import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_backend.usecase.listing.JobListingDB;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * A demo class to provide a source of Job Listings to simulate something like, LinkedIn.
 *
 */

public class DemoJobListingSource {


    private JobListingDB jobDB = new JobListingDB();
    private final static String LISTING_SAVE_LOCATION = "DemoListings" + File.separator;

    public DemoJobListingSource(){
        loadSavedListings();
    }

    public void loadSavedListings(){
        jobDB = new JobListingDB(DataFormat.loadEntriesFromDirectory(LISTING_SAVE_LOCATION));

        for (Entry entry:
             jobDB) {
            entry.updateData(JobListing.UID, null);
        }
    }

    public JobListingDB getJobDB() {
        return jobDB;
    }

    public ArrayList<Map<String, Object>> search(SearchQuery query){
        loadSavedListings();
        HashMap<String, ArrayList<JobListing>> results = Search.searchProvidedDatabase(query, jobDB);

        return getCommon(results);
    }

    private ArrayList<Map<String, Object>> getCommon(HashMap<String, ArrayList<JobListing>> entriesMap){
        HashSet<JobListing> allListings = new HashSet<>();

        ArrayList<Map<String, Object>> finalResults = new ArrayList<>();

        for (String key:
             entriesMap.keySet()) {
            allListings.addAll(entriesMap.get(key));
        }

        for (JobListing entry:
             allListings) {
            boolean inAll = true;
            for (String key:
                 entriesMap.keySet()) {
                ArrayList<JobListing> entries = entriesMap.get(key);
                if (entries.size() != 0 && !entries.contains(entry)){
                    inAll = false;
                    break;
                }
            }
            if (inAll){
                finalResults.add(entry.serialize());
            }
        }
        return finalResults;
    }

    public Map<String, Object> getDemoListingByDSID(String dsid){
        loadSavedListings();
        for (Entry entry:
             jobDB) {
            if (dsid.equals(entry.getData(DemoSourceJobListing.DEMO_SOURCE_ID))){
                return entry.serialize();
            }
        }
        return null;
    }
}
