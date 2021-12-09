package com.minortechnologies.workr_frontend.entities.listing;

import com.minortechnologies.workr_frontend.entities.Entry;
import com.minortechnologies.workr_frontend.usecase.fileio.MalformedDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public abstract class JobListing extends Entry {

    public static final String UID = "uuid";
    public static final String LISTING_TYPE = "listingType";
    public static final String TITLE = "title";
    public static final String LOCATION = "location";
    public static final String PAY = "pay";
    public static final String JOB_TYPE = "jobType";
    public static final String QUALIFICATIONS = "qualifications";
    public static final String REQUIREMENTS = "requirements";
    public static final String APPLICATION_REQUIREMENTS = "applicationReq";
    public static final String DESCRIPTION = "description";
    public static final String LISTING_DATE = "listingDate";
    public static final String CROSS_PLATFORM_DUPLICATES = "crossPlatformDuplicates";
    public static final String JOB_FIELD = "jobField";

    public static final String[] KEYS = new String[] {UID, LISTING_DATE, TITLE, LOCATION, PAY, JOB_TYPE, QUALIFICATIONS,
            LISTING_TYPE, REQUIREMENTS, APPLICATION_REQUIREMENTS, DESCRIPTION, CROSS_PLATFORM_DUPLICATES};

    private boolean saved;
    private ArrayList<String> cpdUUIDS; // UUIDS of cross platform duplicates.

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public ArrayList<String> getCpdUUIDS(){
        return cpdUUIDS;
    }

    @Override
    public HashMap<String, Object> serialize() {
        HashMap<String, Object> serializeDataMap = new HashMap<>();

        for (String key:
             KEYS) {

            if (Objects.equals(key, CROSS_PLATFORM_DUPLICATES)){
                serializeDataMap.put(key, getCPDUUIDS());
            }
            else{
                serializeDataMap.put(key, getData(key));
            }
        }
        return serializeDataMap;
    }

    @Override
    public void deserialize(Map<String, Object> entryDataMap) throws MalformedDataException {

        for (String key:
             KEYS) {
            if (!entryDataMap.containsKey(key)){
                throw new MalformedDataException(Entry.MALFORMED_EXCEPTION_MSG);
            }

            Object data = entryDataMapDataParse(entryDataMap, key);
            addData(key, data);
        }
    }

    @Override
    public synchronized void updateEntry(Entry entry){
        Map<String, Object> newData = entry.serialize();
        newData.remove(UID);
        updateEntry(newData);
    }

    @Override
    public synchronized void updateEntry(Map<String, Object> entryDataMap){
        for (String key:
                KEYS) {
            if (!entryDataMap.containsKey(key)){
                continue;
            }

            Object data = entryDataMapDataParse(entryDataMap, key);

            updateData(key, data);
        }
    }

    private Object entryDataMapDataParse(Map<String, Object> entryDataMap, String key) {
        Object data = entryDataMap.get(key);
        switch (key){
            case CROSS_PLATFORM_DUPLICATES:
                ArrayList<JobListing> cpdList = new ArrayList<>();
                cpdUUIDS = (ArrayList<String>) entryDataMap.get(key);
                data = cpdList;
                break;
            case UID:
                if (entryDataMap.get(UID) == null){
                    data = UUID.randomUUID().toString();
                }
                break;
        }
        return data;
    }

    private ArrayList<String> getCPDUUIDS(){
        Object cpdsObj = getData(CROSS_PLATFORM_DUPLICATES);
        ArrayList<JobListing> cpdsList;
        if (cpdsObj instanceof ArrayList){
            cpdsList = (ArrayList<JobListing>) cpdsObj;
            ArrayList<String> cpduuids = new ArrayList<>();

            for (JobListing entry:
                 cpdsList) {
                cpduuids.add(entry.getUUID());
            }
            return cpduuids;
        }
        return null;
    }

    @Override
    public String getSerializedFileName() {
        return getUUID();
    }

    public String getUUID(){
        if (getData(UID) == null){
            updateData(UID, UUID.randomUUID().toString());
        }
        return (String) getData(UID);
    }

    public ListingType getListingType(){
        return (ListingType) getData(JobListing.LISTING_TYPE);
    }

    public JobType getJobType(){
        return (JobType) getData(JOB_TYPE);
    }

    @Override
    public int hashCode(){
        String rep = getData(TITLE) + ((JobType) getData(JOB_TYPE)).name();
        return rep.hashCode();
    }

    public String getTitle(){
        return (String) getData(TITLE);
    }

    public LocalDate getListingDate(){
        if (getData(LISTING_DATE) instanceof LocalDateTime){
            updateData(LISTING_DATE, ((LocalDateTime) getData(LISTING_DATE)).toLocalDate());
        }
        return (LocalDate) getData(LISTING_DATE);
    }

    public abstract boolean isEquivalent(JobListing other);
}
