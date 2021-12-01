package com.minortechnologies.workr_frontend.usecase.factories;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.listing.JobType;
import com.minortechnologies.workr_frontend.entities.listing.ListingType;
import com.minortechnologies.workr_frontend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_frontend.entities.user.Experience;
import com.minortechnologies.workr_frontend.entities.user.User;

import java.util.*;

/**
 * This class takes a HashMap and correctly casts the data to the correct types.
 *
 * This is used so that Entry.Deserialize(Entry.Serialize) works, which before this class
 * was created was not the case, as when deserializing, most of the data will be Strings and
 * not be parsed into a correct type, such as LocalDateTimes or Enums.
 *
 * Essentially, the type casting and string-data parsing was offloaded from the Entry subclass
 * to this new class.
 *
 */
public class EntryDataMapTypeCaster {

    //TODO: Maybe replaces these with hashsets to remove duplicates.

    public static final String[] DATE_TIME_KEYS = new String[] {JobListing.LISTING_DATE, Experience.START_TIME,
            Experience.END_TIME, SearchQuery.DATE_TIME};
    public static final String[] STRING_ARRAYS = new String[] {JobListing.CROSS_PLATFORM_DUPLICATES};

    public static final String[] HASHSETS = new String[] {User.WATCHED_JOB_LISTINGS};

    public static final String[] ENUMS = new String[] {JobListing.JOB_TYPE, JobListing.LISTING_TYPE};

    public static final String[] RECURSIVE_LIST = new String[] {User.WATCHED_SEARCH_QUERIES, User.REL_WORK_EXP,
            User.UREL_WORK_EXP, User.LEADERSHIP};

    public void convertValueTypes(Map<String, Object> dataMap){
        for (String key:
             dataMap.keySet()) {
            Object data = dataMap.get(key);
            if (Arrays.asList(DATE_TIME_KEYS).contains(key)){
                data = ICreateEntry.parseDateTime(data);
            }
            else if (Arrays.asList(STRING_ARRAYS).contains(key) && data instanceof String[] ){
                data = stringArrayToList((String[]) data);
            }
            else if (Arrays.asList(ENUMS).contains(key)){
                data = enumParse(key, data);
            }
            else if (Arrays.asList(RECURSIVE_LIST).contains(key)){

                List<Map<String, Object>> container = (List<Map<String, Object>>) data;
                for (Map<String, Object> item:
                     container) {
                    convertValueTypes(item);
                }
            }
            else if (Arrays.asList(HASHSETS).contains(key)){

                ArrayList<Object> data2 = (ArrayList<Object>) data;
                data = toHashSet(key, data2);
            }
            dataMap.replace(key, data);
        }
    }

    private Object toHashSet(String key, ArrayList<Object> data) {
        switch (key){
            case User.WATCHED_JOB_LISTINGS:
                return toStringHashSet(data);
        }
        return null;
    }


    private HashSet<String> toStringHashSet(ArrayList<Object> data){
        HashSet<String> finalSet = new HashSet<>();
        for (Object string:
                data) {
            finalSet.add((String) string);
        }
        return finalSet;
    }


    private ArrayList<String> stringArrayToList(String[] array){
        return new ArrayList<>(Arrays.asList(array));
    }

    private Object enumParse(String key, Object value){
        switch (key){
            case JobListing.JOB_TYPE:
                return JobType.valueOf((String) value);
            case JobListing.LISTING_TYPE:
                return ListingType.valueOf((String) value);
            default:
                return null;
        }
    }

}
