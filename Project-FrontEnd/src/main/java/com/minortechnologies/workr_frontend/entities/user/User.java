package com.minortechnologies.workr_frontend.entities.user;

import com.minortechnologies.workr_frontend.entities.Entry;
import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_frontend.main.Main;
import com.minortechnologies.workr_frontend.usecase.fileio.MalformedDataException;
import com.minortechnologies.workr_frontend.usecase.security.Security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class User extends Entry {

    private String[] watchedListingsUUID;


    // Some constants for keys

    /*
    Some differences from the User class in backend:
    This one does not store any password (and salt) information, instead, storing a token.

     */
    public static final String ACCOUNT_NAME = "accountName";
    public static final String WATCHED_JOB_LISTINGS = "watchedJobListings";
    public static final String LOGIN = "login";
    public static final String WATCHED_SEARCH_QUERIES = "watchedSearchQueries";
    public static final String SKILL_SET = "skillSet"; // ArrayList<String>
    public static final String REL_WORK_EXP = "relWorkExp"; // ArrayList<Experience>
    public static final String UREL_WORK_EXP = "urelWorkExp"; // ArrayList<Experience>
    public static final String LEADERSHIP = "leadership"; // ArrayList<Experience>
    public static final String LOCATION = "location"; // String
    public static final String AWARDS = "awards"; // ArrayList<String>
    public static final String INCENTIVE = "incentive"; // ArrayList<String>
    public static final String TOKEN = "token"; // String
    public static final String SCORES = "scores";
    public static final String GPA = "gpa"; // double
    public static final String[] KEYS = new String[] {ACCOUNT_NAME, WATCHED_JOB_LISTINGS, LOGIN,
            WATCHED_SEARCH_QUERIES, SKILL_SET, REL_WORK_EXP, UREL_WORK_EXP, LEADERSHIP, LOCATION, AWARDS,
            INCENTIVE, SCORES, GPA};

    /**
     * Creates a User entry with no data for Deserialization or for UnitTests.
     */
    public User(){
        super();
    }


    /**
     * Creates a new User with the provided data.
     *
     * @param accountName the name of the account
     * @param login a unique login
     * @param token a token, generated by the Backend.
     */
    public User(String accountName, String login, String token){
        super();
        addData(ACCOUNT_NAME, accountName);
        addData(LOGIN, login);
        addData(WATCHED_JOB_LISTINGS, new HashSet<String>());
        addData(WATCHED_SEARCH_QUERIES, new HashSet<SearchQuery>());
        addData(SKILL_SET, new ArrayList<String>());
        addData(REL_WORK_EXP, new ArrayList<Experience>());
        addData(UREL_WORK_EXP, new ArrayList<Experience>());
        addData(LEADERSHIP, new ArrayList<Experience>());
        addData(LOGIN, null);
        addData(AWARDS, null);
        addData(INCENTIVE, null);
        addData(TOKEN, token);
        addData(SCORES, new ArrayList<Score>());
        addData(GPA, 0.0d);
    }

    /**
     * Watched Listings in a user's profile are stored just as UUIDs. This method returns the entries associated
     * with the UUIDs
     *
     * @return A HashSet containing UUIDs of watched listings
     */
    public HashSet<String> getWatchedListings() {

        return (HashSet<String>) getData(WATCHED_JOB_LISTINGS);
    }

    /**
     * Adds a listing to watchedListings. If listing is already in watchedListing, returns false, otherwise returns true.
     *
     * @param jobListing adds this listing to the user's watched listings
     * @return a boolean, false if listing is already in watchedListing, otherwise returns true.
     */
    public boolean addListingToWatch(JobListing jobListing){
        jobListing.setSaved(true);

        return ((HashSet<String>) getData(WATCHED_JOB_LISTINGS)).add(jobListing.getUUID());
    }

    @Override
    public synchronized HashMap<String, Object> serialize() {

        HashMap<String, Object> preSerializedData = new HashMap<>();

        HashSet<String> watchedJobListings = getWatchedListings();
        String[] watchedJobListingUUID = new String[watchedJobListings.size()];

        int i = 0;
        for (String listing:
             watchedJobListings) {
            watchedJobListingUUID[i] = listing;
            i++;
        }

        for (String key:
             KEYS) {
            Object data = getData(key);
            switch (key){
                case WATCHED_JOB_LISTINGS:
                    data = watchedJobListingUUID;
                    break;
                case WATCHED_SEARCH_QUERIES:
                    data = getSerializedSearchQueries();
                    break;
                case REL_WORK_EXP:
                case UREL_WORK_EXP:
                case LEADERSHIP:
                case SCORES:
                    ArrayList<Entry> dataMaps = (ArrayList<Entry>) getData(key);
                    data = getNestedSerializationData(dataMaps);
                    break;
            }

            preSerializedData.put(key, data);
        }
        return preSerializedData;
    }

    private ArrayList<HashMap<String, Object>> getSerializedSearchQueries(){

        return getNestedSerializationData((HashSet<Entry>) getData(WATCHED_SEARCH_QUERIES));
    }

    @Override
    public synchronized void deserialize(Map<String, Object> entryDataMap) throws MalformedDataException {

        if (entryDataMap.keySet().size() != KEYS.length){
            throw new MalformedDataException(MALFORMED_EXCEPTION_MSG);
        }

        for (String key:
             KEYS) {
            Object data = entryDataMap.get(key);
            addData(key, data);
        }
    }

    @Override
    public String getSerializedFileName() {
        return (String) getData(LOGIN);
    }


    @Override
    public synchronized void updateEntry(Map<String, Object> entryDataMap) {
        for (String key:
                KEYS) {
            if (!entryDataMap.containsKey(key)){
                Object data = entryDataMap.get(key);
                updateData(key, data);
            }
        }
    }

    @Override
    public synchronized void updateEntry(Entry entry) {
        Map<String, Object> entryData = entry.serialize();
        updateEntry(entryData);
    }

    @Override
    public int hashCode(){
        return getData(LOGIN).hashCode();
    }


}
