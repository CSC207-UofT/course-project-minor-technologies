package com.minortechnologies.workr_backend.entities.user;

import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_backend.main.Main;
import com.minortechnologies.workr_backend.networkhandler.Application;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;
import com.minortechnologies.workr_backend.usecase.security.Security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class User extends Entry {

    private String[] watchedListingsUUID;


    // Some constants for keys

    public static final String ACCOUNT_NAME = "accountName";
    public static final String WATCHED_JOB_LISTINGS = "watchedJobListings";
    public static final String LOGIN = "login";
    public static final String HASHED_PASSWORD = "hashedPassword";
    public static final String SALT = "salt";
    public static final String WATCHED_SEARCH_QUERIES = "watchedSearchQueries";
    public static final String SKILL_SET = "skillSet"; // ArrayList<String>
    public static final String REL_WORK_EXP = "relWorkExp"; // ArrayList<Experience>
    public static final String UREL_WORK_EXP = "urelWorkExp"; // ArrayList<Experience>
    public static final String LEADERSHIP = "leadership"; // ArrayList<Experience>
    public static final String LOCATION = "location"; // String
    public static final String AWARDS = "awards"; // ArrayList<String>
    public static final String INCENTIVE = "incentive"; // ArrayList<String>
    public static final String EMAIL = "email"; // String
    public static final String SCORES = "scores"; // ArrayList<Score>
    public static final String[] KEYS = new String[] {ACCOUNT_NAME, WATCHED_JOB_LISTINGS, LOGIN, HASHED_PASSWORD,
            WATCHED_SEARCH_QUERIES ,SALT, SKILL_SET, REL_WORK_EXP, UREL_WORK_EXP, LEADERSHIP, LOCATION, AWARDS,
            INCENTIVE, EMAIL, SCORES};

    /**
     * Creates a User entry with no data for Deserialization or for UnitTests.
     */
    public User(){
        super();
    }

    /**
     * A demo constructor, this should never be called outside of demo stuff
     *
     * TODO: Delete this constructor
     * @param login
     */
    public User(String login){
        this("demo", login, "testEmail@Email.com", null, null);
        byte[] saltArr = Security.generateSalt();
        String salt = Security.toHex(saltArr);
        String hashedPassword = Security.toHex(Security.generateHash("demo", saltArr));

        updateData(HASHED_PASSWORD, hashedPassword);
        updateData(SALT, salt);
    }


    /**
     * Creates a new User with the provided data.
     *
     * @param accountName
     * @param login
     * @param passwordHash
     * @param salt
     */
    public User(String accountName, String login, String email, String passwordHash, String salt){
        super();
        addData(ACCOUNT_NAME, accountName);
        addData(LOGIN, login);
        addData(HASHED_PASSWORD, passwordHash);
        addData(SALT, salt);
        addData(WATCHED_JOB_LISTINGS, new HashSet<String>());
        addData(WATCHED_SEARCH_QUERIES, new HashSet<SearchQuery>());
        addData(SKILL_SET, new ArrayList<String>());
        addData(REL_WORK_EXP, new ArrayList<Experience>());
        addData(UREL_WORK_EXP, new ArrayList<Experience>());
        addData(LEADERSHIP, new ArrayList<Experience>());
        addData(LOGIN, null);
        addData(AWARDS, null);
        addData(INCENTIVE, null);
        addData(EMAIL, email);
        addData(SCORES, new ArrayList<Score>());
    }

    public boolean matchPassword(String password){
        return password.equals(getData(HASHED_PASSWORD));
    }

    /**
     * Watched Listings in a user's profile are stored just as UUIDs. This method returns the entries associated
     * with the UUIDs
     *
     * @return
     */
    public HashSet<JobListing> getWatchedListings() {
        HashSet<String> uuids = (HashSet<String>) getData(WATCHED_JOB_LISTINGS);
        HashSet<JobListing> watchedListings = new HashSet<>();

        for (String uuid:
             uuids) {
            JobListing listing = Application.getLocalCache().getListingFromUUID(uuid);
            watchedListings.add(listing);
        }

        return watchedListings;
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

    public boolean addListingToWatch(String jobListingUUID){
        return ((HashSet<String>) getData(WATCHED_JOB_LISTINGS)).add(jobListingUUID);
    }

    @Override
    public synchronized HashMap<String, Object> serialize() {

        HashMap<String, Object> preSerializedData = new HashMap<>();

        HashSet<JobListing> watchedJobListings = getWatchedListings();
        String[] watchedJobListingUUID = new String[watchedJobListings.size()];

        int i = 0;
        for (JobListing listing:
             watchedJobListings) {
            watchedJobListingUUID[i] = listing.getUUID();
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


    /**
     * for security purposes, updateEntry alter password, salt, and email of a user.
     *
     * @param entryDataMap
     */
    @Override
    public synchronized void updateEntry(Map<String, Object> entryDataMap) {
        entryDataMap.remove(User.HASHED_PASSWORD);
        entryDataMap.remove(User.SALT);
        entryDataMap.remove(User.EMAIL);
        for (String key:
                KEYS) {
            if (entryDataMap.containsKey(key) && entryDataMap.get(key) != null){
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

    public String getSalt() {
        return (String) getData(SALT);
    }

    public String[] getWatchedListingsUUID() {
        HashSet<JobListing> watchedListings = getWatchedListings();
        String[] uuids = new String[watchedListings.size()];
        int i = 0;
        for (JobListing listing:
             watchedListings) {
            uuids[i] = listing.getUUID();
        }
        return uuids;
    }

    public void changePassword(String password){
        byte[] saltArr = Security.generateSalt();
        String newSalt = Security.toHex(saltArr);
        String newHashedPass = Security.toHex(Security.generateHash(password, saltArr));
        this.updateData(SALT, newSalt);
        this.updateData(HASHED_PASSWORD, newHashedPass);
    }
}
