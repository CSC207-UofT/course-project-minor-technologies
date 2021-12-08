package com.minortechnologies.workr_backend.usecase.factories;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.listing.JobType;
import com.minortechnologies.workr_backend.entities.listing.ListingType;
import com.minortechnologies.workr_backend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_backend.entities.user.Experience;
import com.minortechnologies.workr_backend.entities.user.User;

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

    public static final String[] DOUBLES = new String[] {User.GPA};

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
                try{
                    data = stringArrayToList((String[]) data);
                }
                catch (ClassCastException e){
                    e.printStackTrace();
                    data = null;
                }
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
                ArrayList<Object> data2;
                if (data instanceof String[]){
                    String[] data3 = (String[]) data;
                    data2 = new ArrayList<>(Arrays.asList(data3));
                }
                else{
                    data2 = (ArrayList<Object>) data;
                }

                data = toHashSet(key, data2);
            }
            else if (Arrays.asList(DOUBLES).contains(key)){
                if (data instanceof Float){
                    data = (double) data;
                }
            }
            dataMap.replace(key, data);
        }
    }

    private Object toHashSet(String key, Iterable<Object> data) {
        switch (key){
            case User.WATCHED_JOB_LISTINGS:
                return toStringHashSet(data);
        }
        return null;
    }


    private HashSet<String> toStringHashSet(Iterable<Object> data){
        HashSet<String> finalSet = new HashSet<>();
        for (Object string:
                data) {
            if (string instanceof String){
                finalSet.add((String) string);
            }
        }
        return finalSet;
    }


    private ArrayList<String> stringArrayToList(String[] array){
        return new ArrayList<>(Arrays.asList(array));
    }

    private Object enumParse(String key, Object value){
        try{
            switch (key){
                case JobListing.JOB_TYPE:
                    if (value instanceof JobType){
                        return value;
                    }
                    return JobType.valueOf((String) value);
                case JobListing.LISTING_TYPE:
                    if (value instanceof ListingType){
                        return value;
                    }
                    return ListingType.valueOf((String) value);
                default:
                    return null;
            }
        }
        catch (IllegalArgumentException | ClassCastException e){
            e.printStackTrace();
            return null;
        }
    }

    public static int malformedDataCount(Map<String, Object> original, Map<String, Object> casted){

        int counter = 0;

        for (String key:
             original.keySet()) {
            if (original.get(key) != null && casted.get(key) == null){
                counter++;
            }
        }

        return counter;
    }

}
