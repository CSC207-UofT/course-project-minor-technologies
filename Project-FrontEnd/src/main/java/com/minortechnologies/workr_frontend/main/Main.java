package com.minortechnologies.workr_frontend.main;

import java.time.LocalDateTime;
import java.util.*;

import com.minortechnologies.workr_frontend.controllers.backgroundoperations.BackgroundOperations;
import com.minortechnologies.workr_frontend.controllers.localcache.LocalCache;
import com.minortechnologies.workr_frontend.controllers.search.Search;
import com.minortechnologies.workr_frontend.controllers.usermanagement.UserManagement;
import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_frontend.entities.listing.JobType;
import com.minortechnologies.workr_frontend.entities.user.User;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static LocalCache localCache;

    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    public static LocalCache getLocalCache() {
        return localCache;
    }
}
