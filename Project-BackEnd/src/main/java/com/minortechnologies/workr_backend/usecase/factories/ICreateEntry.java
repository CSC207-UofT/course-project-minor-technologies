package com.minortechnologies.workr_backend.usecase.factories;

import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_backend.entities.user.Experience;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.usecase.factories.joblisting.ICreateJobListing;
import com.minortechnologies.workr_backend.usecase.factories.userfactory.CreateExperience;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;
import com.minortechnologies.workr_backend.usecase.factories.searchquery.CreateSearchQuery;
import com.minortechnologies.workr_backend.usecase.factories.userfactory.CreateUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;

public interface ICreateEntry {

    Entry create(Map<String, Object> entryDataMap) throws MalformedDataException;

    /**
     * Creates an Entry from the provided hashmap. Returns an Entry of a specific type dependent on the data in the
     * HashMap.
     *
     * @param entryDataMap a Map containing data for the Entry
     * @return The entry created from the provided data.
     * @throws MalformedDataException If the data provided is missing any keys.
     */
    static Entry createEntry(Map<String, Object> entryDataMap) throws MalformedDataException{
        return createEntry(entryDataMap, true);
    }

    static Entry createEntry(Map<String, Object> entryDataMap, boolean parseData) throws MalformedDataException {

        if (parseData){
            (new EntryDataMapTypeCaster()).convertValueTypes(entryDataMap);
        }

        if (entryDataMap.containsKey(User.LOGIN)){
            return new CreateUser().create(entryDataMap);
        }
        else if (entryDataMap.containsKey(JobListing.LISTING_TYPE)){
            return ICreateJobListing.createListing(entryDataMap);
        }
        else if (entryDataMap.containsKey(SearchQuery.SEARCH_TERMS)){
            return new CreateSearchQuery().create(entryDataMap);
        }
        else if (entryDataMap.containsKey(Experience.EXPERIENCE_TITLE)){
            return new CreateExperience().create(entryDataMap);
        }
        else{
            throw new MalformedDataException("Cannot Identify Entry Type");
        }
    }

    ArrayList<String> verifyMapIntegrity(Map<String, Object> entryDataMap);

    static String missingKeyInfo(ArrayList<String> keys, String type){
        StringBuilder msg = new StringBuilder("JSON data for {" + type + "} listing missing keys:");
        for (String key :
                keys) {
            msg.append(" {").append(key).append("},");
        }

        return msg.toString();
    }

    static LocalDate parseDateTime(Object dateString){
        if (dateString instanceof String){
            try{
                if (((String) dateString).contains("T")){
                    LocalDateTime ldt = LocalDateTime.parse((String) dateString);
                    return ldt.toLocalDate();
                }
                else{
                    return LocalDate.parse((String) dateString);
                }
            }
            catch (DateTimeParseException e){
                return LocalDate.now();
            }
        }
        if (dateString instanceof LocalDate){
            return (LocalDate) dateString;
        }
        else{
            return LocalDate.now();
        }
    }
}
