package com.minortechnologies.workr_backend.usecase.factories.userfactory;

import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.user.Experience;
import com.minortechnologies.workr_backend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;

import java.util.ArrayList;
import java.util.Map;

public class CreateExperience implements ICreateEntry {
    @Override
    public Entry create(Map<String, Object> entryDataMap) throws MalformedDataException {

        Experience experience = new Experience();
        experience.deserialize(entryDataMap);

        return experience;
    }

    @Override
    public ArrayList<String> verifyMapIntegrity(Map<String, Object> entryDataMap) {
        ArrayList<String> missingKeys = new ArrayList<>();
        for (String key:
                Experience.KEYS) {
            if (!entryDataMap.containsKey(key)){
                missingKeys.add(key);
            }
        }
        return missingKeys;
    }
}
