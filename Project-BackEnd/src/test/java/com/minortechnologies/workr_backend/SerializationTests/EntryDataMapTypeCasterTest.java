package com.minortechnologies.workr_backend.SerializationTests;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.usecase.factories.EntryDataMapTypeCaster;
import org.junit.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class EntryDataMapTypeCasterTest {

    EntryDataMapTypeCaster edmtc;


    @Before
    public void setUp(){
        edmtc = new EntryDataMapTypeCaster();
    }

    /**
     *  Tests that this class correctly parses LocalDateTime.
     */

    @Test
    public void localDateTimeParse(){
        LocalDateTime now = LocalDateTime.now();
        String timeString = now.toString();

        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put(JobListing.LISTING_DATE, timeString);

        edmtc.convertValueTypes(testMap);

        assertEquals(now, testMap.get(JobListing.LISTING_DATE));
    }

    @Test
    public void nestedMap(){
        HashMap<String, Object> testMap = new HashMap<>();

        ArrayList<HashMap<String, Object>> nested1 = new ArrayList<>();

        HashMap<String, Object> nestedMap1 = new HashMap<>();
        HashMap<String, Object> nestedMap2 = new HashMap<>();

        LocalDateTime max = LocalDateTime.MAX;
        String maxString = max.toString();
        nestedMap1.put(JobListing.LISTING_DATE, maxString);
        nestedMap2.put(SearchQuery.DATE_TIME, maxString);

        nested1.add(nestedMap1);
        nested1.add(nestedMap2);

        testMap.put(User.WATCHED_SEARCH_QUERIES, nested1);

        edmtc.convertValueTypes(testMap);

        try{
            ArrayList<HashMap<String, Object>> resultArray = (ArrayList<HashMap<String, Object>>) testMap.get(User.WATCHED_SEARCH_QUERIES);
            LocalDateTime result1 = (LocalDateTime) resultArray.get(0).get(JobListing.LISTING_DATE);
            LocalDateTime result2 = (LocalDateTime) resultArray.get(1).get(SearchQuery.DATE_TIME);

            assertEquals(max, result1);
            assertEquals(max, result2);
        }
        catch (ClassCastException e){
            fail();
        }
    }
}
