package com.minortechnologies.workr_backend.framework.networkhandler;

import com.minortechnologies.workr_backend.controllers.networkhandler.ListingRequestHandler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ListingRequestReceiver {

    @GetMapping("/JobListing/Get/{uuid}")
    public Map<String, Object> getListing(@PathVariable String uuid){
        return ListingRequestHandler.getListing(uuid);
    }

    @GetMapping("/JobListing/GetMultiple")
    public ArrayList<HashMap<String, Object>> getMultipleListing(@RequestBody String[] uuids){
        return ListingRequestHandler.getListing(uuids);
    }

    @GetMapping("/JobListing/Search")
    public ArrayList<HashMap<String, Object>> searchListings(@RequestParam String dateTime, String location, String jobType, String searchTerms){

        String[] terms = searchTerms.split("_");
        StringBuilder searchTermsFinal = new StringBuilder();
        for (String term:
             terms) {
            searchTermsFinal.append(term).append(" ");
        }

        HashMap<String, Object> query = new HashMap<>();
        query.put("dateTime", dateTime);
        query.put("location", location);
        query.put("jobType", jobType);
        query.put("searchTerms", searchTermsFinal.toString());
        return ListingRequestHandler.searchListings(query);
    }

    @GetMapping("/JobListing/Score/{login}")
    public ArrayList<Map<String, Object>> score(@RequestParam String token, @PathVariable String login, @RequestBody String[] payload){
        return ListingRequestHandler.calculateScore(token, login, payload);
    }

    @PostMapping("/JobListing/{login}/CreateCustom")
    public String createCustomListing(@RequestParam String token, @PathVariable String login, @RequestBody Map<String, Object> payload){
        return ListingRequestHandler.createListing(token, login, payload);
    }

    @PostMapping("/JobListing/{login}/UpdateListing")
    public int updateCustomListing(@RequestParam String token, @PathVariable String login, @RequestBody Map<String, Object> payload){
        return ListingRequestHandler.updateListing(token, login, payload);
    }
}
