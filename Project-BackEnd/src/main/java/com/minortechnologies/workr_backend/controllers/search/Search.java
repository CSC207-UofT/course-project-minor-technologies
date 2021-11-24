package com.minortechnologies.workr_backend.controllers.search;

import com.minortechnologies.workr_backend.demo.demosource.DemoJobListingSource;
import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_backend.main.Main;
import com.minortechnologies.workr_backend.networkhandler.Application;
import com.minortechnologies.workr_backend.usecase.IDatabase;
import com.minortechnologies.workr_backend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Search {

    //TODO: Complete methods
    /**
     * Searches the LocalCache (files already loaded by the program) using query.
     *
     * @param query The search query
     * @return com.minortechnologies.workr.Entities.Listings.Listing[] - returns an array of com.minortechnologies.workr.Controllers.Search.Search Listings from the com.minortechnologies.workr.Controllers.LocalCache.LocalCache according to the search query
     */
    public static HashMap<String, ArrayList<JobListing>> searchLocalCache(SearchQuery query){
        IDatabase lcDatabase = Main.getLocalCache().getListingDB();
        return searchProvidedDatabase(query, lcDatabase);
    }

    /**
     * Uses the search query on a provided IDatabase object.
     *
     * @param query
     * @param database
     * @return
     */
    public static HashMap<String, ArrayList<JobListing>> searchProvidedDatabase(SearchQuery query, IDatabase database){
        ArrayList<JobListing> l1 = new ArrayList<>();
        ArrayList<JobListing> l2 = new ArrayList<>();
        ArrayList<JobListing> l3 = new ArrayList<>();
        ArrayList<JobListing> l4 = new ArrayList<>();
        HashMap<String, ArrayList<JobListing>> sorted_list = new HashMap<>();

        for(Entry item: database) {
            if (item instanceof JobListing) {
                JobListing jobListing = (JobListing) item;
                if (search_terms(query, jobListing.getTitle()) || Objects.equals(jobListing.getTitle(), "") || search_terms(query, (String) jobListing.getData(JobListing.DESCRIPTION)))
                    l1.add(jobListing);
                if (query.getLocation().equalsIgnoreCase((String) jobListing.getData(JobListing.LOCATION)) ||
                        Objects.equals(jobListing.getData(JobListing.LOCATION), ""))
                    l2.add(jobListing);
                if (query.getJobType() == jobListing.getJobType())
                    l3.add(jobListing);
                if(query.getDateTime().getYear() == ((LocalDate)jobListing.getData(JobListing.LISTING_DATE)).getYear()){
                    if(query.getDateTime().getMonthValue() < ((LocalDate)jobListing.getData(JobListing.LISTING_DATE)).getMonthValue())
                        l4.add(jobListing);
                    if(query.getDateTime().getMonthValue() == ((LocalDate)jobListing.getData(JobListing.LISTING_DATE)).getMonthValue() &&
                            query.getDateTime().getDayOfMonth() <= ((LocalDate)jobListing.getData(JobListing.LISTING_DATE)).getDayOfMonth())
                        l4.add(jobListing);
                }
            }
        }
        sorted_list.put("terms",l1);
        sorted_list.put("location",l2);
        sorted_list.put("jobtype",l3);
        sorted_list.put("DateTime", l4);
        return sorted_list;
    }

    /**
     *
     * @return boolean
     */
    private static boolean search_terms(SearchQuery query, String title){
        title = title.trim();
        String[] search_terms = query.getSearchTerms().split(" ");

        int prev_index = 0;
        for(int i =0;i<title.length();i++){
            if(title.charAt(i) == ' ')
            {
                String word = title.substring(prev_index, i);
                word = word.trim();
                prev_index = i + 1;
                if(word.equals(""))
                    continue;
                for (String search_term : search_terms) {
                    if (word.equalsIgnoreCase(search_term))
                        return true;
                }

            }
        }
        return false;
    }

    /**
     * Uses API/Scraper (not yet implemented) to process a search query
     *
     * @param query The search query
     * @return com.minortechnologies.workr.Entities.Listings.Listing[] - returns an array of com.minortechnologies.workr.Controllers.Search.Search Listings from the API/Scraper according to the search query
     */
    public static ArrayList<Entry> searchWeb(SearchQuery query){

        ArrayList<Entry> results = new ArrayList<>();

        // Demo Source Job Listings
        DemoJobListingSource djls = Application.getDemoSource();

        ArrayList<Map<String, Object>> djlsResults = djls.search(query);

        for (Map<String, Object> listingData:
                djlsResults) {
            try{
                JobListing listing = (JobListing) ICreateEntry.createEntry(listingData, false);
                results.add(listing);
            }
            catch (MalformedDataException e){
                e.printStackTrace();
            }
        }

        return results;
    }
}
