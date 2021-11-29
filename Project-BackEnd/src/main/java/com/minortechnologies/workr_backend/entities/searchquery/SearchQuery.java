package com.minortechnologies.workr_backend.entities.searchquery;

import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.listing.JobType;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;
import com.minortechnologies.workr_backend.usecase.factories.ICreateEntry;
import org.apache.commons.lang3.SerializationUtils;

import java.time.*;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchQuery object represents a search query that a user would make. These are also stored in the User class for
 * the automatic update function.
 *
 */

public class SearchQuery extends Entry {

    public SearchQuery(String searchTerms, String location, LocalDateTime dateTime, JobType jobType){
        super();
        addData(SEARCH_TERMS, searchTerms);
        addData(LOCATION, location);
        addData(DATE_TIME, dateTime);
        addData(JOB_TYPE, jobType);
    }

    public static final String SEARCH_TERMS = "searchTerms";
    public static final String LOCATION = "location";
    public static final String DATE_TIME = "dateTime";
    public static final String JOB_TYPE = JobListing.JOB_TYPE;
    public static final String[] KEYS = new String[] {SEARCH_TERMS, LOCATION, DATE_TIME, JOB_TYPE};

    /**
     * Creates an empty SearchQuery object for deserialization
     */
    public SearchQuery(){
        super();
    }

    public String getSearchTerms() {
        return (String) getData(SEARCH_TERMS);
    }

    public String getLocation() {
        return (String) getData(LOCATION);
    }

    public LocalDate getDateTime() {

        if (getData(DATE_TIME) instanceof LocalDateTime){
            updateData(DATE_TIME, ((LocalDateTime) getData(DATE_TIME)).toLocalDate());
        }
        return (LocalDate) getData(DATE_TIME);
    }

    public JobType getJobType() {
        return (JobType) getData(JOB_TYPE);
    }

    @Override
    public synchronized HashMap<String, Object> serialize() {
        HashMap<String, Object> dataClone = SerializationUtils.clone(getEntryData());
        HashMap<String, Object> preSerializedData = new HashMap<>();

        for (String key:
             KEYS) {
            preSerializedData.put(key, dataClone.get(key));
        }
        return preSerializedData;
    }

    @Override
    public synchronized void deserialize(Map<String, Object> entryDataMap) throws MalformedDataException {
        for (String key:
             KEYS) {
            Object data = entryDataMap.get(key);
            switch (key){
                case DATE_TIME:
                    data = ICreateEntry.parseDateTime(data);
                    break;
                case JOB_TYPE:
                    if (data instanceof String){
                        data = JobType.valueOf((String) data);
                    }
                    break;
            }
            addData(key, data);
        }
    }

    @Override
    public String getSerializedFileName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void updateEntry(Map<String, Object> entryDataMap) {
        throw new UnsupportedOperationException();
    }

    /**
     * Note, search queries should never need to be updated.
     * @param entry
     */

    @Override
    public synchronized void updateEntry(Entry entry) {
        throw new UnsupportedOperationException();
    }
}
