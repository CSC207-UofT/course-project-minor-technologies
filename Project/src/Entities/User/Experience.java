package Entities.User;

import Entities.Entry;
import UseCase.FileIO.MalformedDataException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Experience extends Entry {

    public static final String EXPERIENCE_TITLE = "experienceTitle";
    public static final String EXPERIENCE_DESCRPTION = "experienceDescription";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String[] KEYS = new String[] {EXPERIENCE_TITLE, EXPERIENCE_DESCRPTION, START_TIME, END_TIME};

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

    /**
     * Checks that the data for the given key is of the correct type, if the key is one used by this class, this method
     * will check if the data is of the correct type, and returns true if it is and false otherwise.
     *
     * If the key is not one used by the class, method will return true.
     *
     * @param key the key for the given data
     * @param data the data to be type checked.
     * @return whether the data is of the correct type.
     */

    public boolean typeCheck(String key, Object data){
        switch (key){
            case EXPERIENCE_TITLE:
                return data instanceof String;
            case EXPERIENCE_DESCRPTION:
                return data instanceof ArrayList;
            case START_TIME:
            case END_TIME:
                return data instanceof LocalDateTime;
            default:
                return true;
        }
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

    /**
     * This entry should never be serialized on its own, It should always be serialized as part of a user entry,
     * therefore it returns null.
     *
     * @return
     */
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
}
