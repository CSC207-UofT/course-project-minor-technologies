package com.minortechnologies.workr_frontend.entities.listing;

import com.minortechnologies.workr_frontend.entities.Entry;
import com.minortechnologies.workr_frontend.usecase.fileio.MalformedDataException;

import java.util.HashMap;
import java.util.Map;

public class DemoSourceJobListing extends JobListing {

    public static final String DEMO_SOURCE_ID = "demoSourceID";

    public DemoSourceJobListing(Map<String, Object> dataMap) throws MalformedDataException{
        super();
        deserialize(dataMap);
    }

    @Override
    public synchronized HashMap<String, Object> serialize(){
        HashMap<String, Object> serializeDataMap = super.serialize();
        serializeDataMap.put(DEMO_SOURCE_ID, this.getData(DEMO_SOURCE_ID));
        return serializeDataMap;
    }

    public void partialDeserialize(Map<String, Object> entryDataMap, boolean partial) throws MalformedDataException {
        super.deserialize(entryDataMap);
        if (!entryDataMap.containsKey(DEMO_SOURCE_ID)){
            throw new MalformedDataException(MALFORMED_EXCEPTION_MSG);
        }
        if (partial){
            updateData(JobListing.UID, null);
        }
        if (entryDataMap.get(DEMO_SOURCE_ID) == null){
            String hs = getTitle() + getListingType();
            String code = Integer.toString(hs.hashCode());
            addData(DEMO_SOURCE_ID, code);
        }
        else {
            addData(DEMO_SOURCE_ID, entryDataMap.get(DEMO_SOURCE_ID));
        }
    }

    @Override
    public synchronized void deserialize(Map<String, Object> entryDataMap) throws MalformedDataException {
        partialDeserialize(entryDataMap, false);
    }

    @Override
    public synchronized void updateEntry(Map<String, Object> entryDataMap){
        super.updateEntry(entryDataMap);
        if (entryDataMap.containsKey(DEMO_SOURCE_ID)){
            updateData(DEMO_SOURCE_ID, entryDataMap.get(DEMO_SOURCE_ID));
        }
    }

    // TODO: this override might be unnecessary
    @Override
    public synchronized void updateEntry(Entry entry){
        super.updateEntry(entry);
    }

    @Override
    public boolean isEquivalent(JobListing other) {
        if (other instanceof DemoSourceJobListing){
            String otherDSID = (String) other.getData(DEMO_SOURCE_ID);
            String thisDSID = (String) getData(DEMO_SOURCE_ID);
            return (thisDSID.equals(otherDSID));
        }
        return false;
    }
}
