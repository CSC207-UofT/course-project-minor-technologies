package com.minortechnologies.workr_backend.entities.employer;

import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;
import org.apache.commons.lang3.SerializationUtils;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL") // this class has not be implemented in any meaningful capacity.
public class Employer extends Entry {

    public final String EMPLOYER_NAME = "employerName";
    public final String EMPLOYER_LISTINGS = "employerListings";

    @Override
    public synchronized HashMap<String, Object> serialize() {

        return SerializationUtils.clone(getEntryData());
    }

    @Override
    public synchronized void deserialize(Map<String, Object> entryDataMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized String getSerializedFileName() {
        return null;
    }

    @Override
    public synchronized void updateEntry(Map<String, Object> entryDataMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void updateEntry(Entry entry) {
        throw new UnsupportedOperationException();
    }
}
