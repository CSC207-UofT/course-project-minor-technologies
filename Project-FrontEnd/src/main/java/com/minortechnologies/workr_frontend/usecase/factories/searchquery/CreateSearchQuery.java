package com.minortechnologies.workr_frontend.usecase.factories.searchquery;

import com.minortechnologies.workr_frontend.entities.Entry;
import com.minortechnologies.workr_frontend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_frontend.usecase.fileio.MalformedDataException;
import com.minortechnologies.workr_frontend.usecase.factories.ICreateEntry;

import java.util.ArrayList;
import java.util.Map;

public class CreateSearchQuery implements ICreateEntry {

    @Override
    public Entry create(Map<String, Object> entryDataMap) throws MalformedDataException {
        SearchQuery query = new SearchQuery();
        query.deserialize(entryDataMap);
        return query;
    }

    @Override
    public ArrayList<String> verifyMapIntegrity(Map<String, Object> entryDataMap) {
        ArrayList<String> missingKeys = new ArrayList<>();
        for (String key:
                SearchQuery.KEYS) {
            if (!entryDataMap.containsKey(key)){
                missingKeys.add(key);
            }
        }
        return missingKeys;
    }
}
