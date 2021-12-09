package com.minortechnologies.workr_backend.entities.listing;

import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;

import java.util.HashMap;
import java.util.Map;

public class CustomJobListing extends JobListing{

    public static final String ORIGIN = "origin";

    /**
     * Creates an empty CustomJobListing, with only the data for the title.
     * @param title The title of the custom job listing
     */
    public CustomJobListing(String title){
        super();
        addData(TITLE, title);

        HashMap<String, Object> data = new HashMap<>();
        for (String key:
                KEYS) {
            data.put(key, null);
        }
        data.put(ORIGIN, null);
        try {
            deserialize(data);
        } catch (MalformedDataException e) {
            e.printStackTrace();
        }
    }

    public CustomJobListing(Map<String, Object> dataMap) throws MalformedDataException {
        super();
        deserialize(dataMap);
    }

    @Override
    public synchronized HashMap<String, Object> serialize(){
        HashMap<String, Object> serializedDataMap = super.serialize();
        serializedDataMap.put(ORIGIN, getData(ORIGIN));
        return serializedDataMap;
    }

    @Override
    public synchronized void deserialize(Map<String, Object> entryDataMap) throws MalformedDataException {
        super.deserialize(entryDataMap);
        if (!entryDataMap.containsKey(ORIGIN)){
            throw new MalformedDataException(MALFORMED_EXCEPTION_MSG);
        }
        addData(ORIGIN, entryDataMap.get(ORIGIN));
    }

    @Override
    public synchronized void updateEntry(Map<String, Object> entryDataMap){
        super.updateEntry(entryDataMap);
        if (entryDataMap.containsKey(ORIGIN)){
            updateData(ORIGIN, entryDataMap.get(ORIGIN));
        }
    }

    /**
     * For Custom Listings, for now, we shall assume a user would not
     * create the same listing twice.
     *
     * @param other the other listing to be compared to
     * @return whether the other listing is equivalent.
     */
    @Override
    public boolean isEquivalent(JobListing other) {
        return false;
    }


}
