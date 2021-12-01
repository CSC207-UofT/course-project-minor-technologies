package com.minortechnologies.workr_backend.entities.user;

import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;


import java.util.HashMap;
import java.util.Map;

public class Score extends Entry {

    public static final String UID = "uuid"; // string
    public static final String SCORE = "score"; // int
    public static final String[] KEYS = new String[] {UID, SCORE};

    @Override
    public synchronized HashMap<String, Object> serialize() {
        HashMap<String, Object> dataMap = new HashMap<>();
        for (String key:
                KEYS) {
            Object data = getData(key);
            dataMap.put(key, data);
        }
        return dataMap;
    }

    @Override
    public synchronized void deserialize(Map<String, Object> entryDataMap) throws MalformedDataException {
        for (String key:
                KEYS) {
            Object data = entryDataMap.get(key);
            addData(key, data);
        }
    }

    @Override
    public String getSerializedFileName() {
        return null;
    }

    @Override
    public synchronized void updateEntry(Map<String, Object> entryDataMap) {
        for (String key:
                KEYS) {
            Object data = entryDataMap.get(key);
            updateData(key, data);
        }
    }

    @Override
    public synchronized void updateEntry(Entry entry) {
        Map<String, Object> entryDataMap = entry.serialize();

        updateEntry(entryDataMap);
    }

    @Override
    public synchronized boolean addData(String key, Object data){
        if (typeCheck(key, data)){
            return super.addData(key, data);
        }
        return false;
    }

    @Override
    public synchronized boolean updateData(String key, Object data) {
        if (typeCheck(key, data)) {
            return super.updateData(key, data);
        }
        return false;
    }

    public boolean typeCheck(String key, Object data){
        switch (key){
            case UID:
                return data instanceof String;
            case SCORE:
                return data instanceof Integer;
            default:
                return true;
        }
    }
}
