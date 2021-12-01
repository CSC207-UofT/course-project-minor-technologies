package com.minortechnologies.workr_backend.main;

import java.time.LocalDateTime;
import java.util.*;

import com.minortechnologies.workr_backend.controllers.backgroundoperations.BackgroundOperations;
import com.minortechnologies.workr_backend.controllers.localcache.LocalCache;
import com.minortechnologies.workr_backend.controllers.search.Search;
import com.minortechnologies.workr_backend.controllers.usermanagement.UserManagement;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_backend.entities.listing.JobType;
import com.minortechnologies.workr_backend.entities.user.User;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static User user;

    private static LocalCache localCache;

    public static UserManagement getUserManagement() {
        return userManagement;
    }

    public static void setUserManagement(UserManagement userManagement) {
        Main.userManagement = userManagement;
    }

    private static UserManagement userManagement;

    public static void setLocalCache(LocalCache localCache) {
        Main.localCache = localCache;
    }

    public static LocalCache getLocalCache(){
        return localCache;
    }

    public static void init(){
        localCache = new LocalCache();

        localCache.loadSavedListings();

        userManagement = new UserManagement();

        localCache.loadSavedListings();

        BackgroundOperations.startBackgroundLoop();
    }

    public static void main(String[] args) {


        localCache = new LocalCache();

        userManagement = new UserManagement();

        localCache.loadSavedListings();

        Scanner c = new Scanner(System.in); // creating console scanner

        System.out.println("Enter name:");
        user = new User(c.nextLine());

        BackgroundOperations.startBackgroundLoop();

        label:
        while(true) {

            System.out.println("What would you like to do?\nType \"help\" for commands.");
            String command = c.next().toLowerCase(Locale.ROOT);

            switch (command) {
                case "help":
                    System.out.println(help());
                    break;
                case "exit":
                    break label;
                case "search":
                    String searchTerms;
                    String location;
                    LocalDateTime dateTime;
                    JobType jobType;

                    System.out.println("Enter all search terms:");
                    searchTerms = c.next();

                    System.out.println("Enter location");
                    location = c.next();

                    dateTime = dateTimeInput(c);

                    jobType = jobTypeInput(c);


                    SearchQuery query = new SearchQuery(searchTerms, location, dateTime, jobType);

                    HashMap<String, ArrayList<JobListing>> relevantListings = Search.searchLocalCache(query);
                    ArrayList<JobListing> toDisplay = relevantListings.get("terms");

                    System.out.println("Relevant listings:");

                    // printing all relevant listings
                    for (JobListing jobListing : toDisplay) {
                        System.out.println(jobListing.getData(JobListing.TITLE) + ", " + jobListing.getData(JobListing.LOCATION));
                    }

                    viewListing(toDisplay, c);


                    break;
                case "saved":
                    if (user.getWatchedListings().size() == 0){
                        print("You currently do not have any saved listings!\nView a listing with 'com.minortechnologies.workr.Controllers.Search.Search' to save it!");
                    }
                    else{
                        ArrayList<JobListing> jobListings = new ArrayList<>();
                        for (JobListing jobListing :
                             user.getWatchedListings()) {
                            jobListings.add(localCache.getListingFromUUID(jobListing.getUUID()));
                        }
                        viewListing(jobListings, c);
                    }
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }

        System.out.println("End of demo");

        // demo(com.minortechnologies.workr.Framework.FileIO.FileIO.WORK_PATH);
    }

    private static void viewListing(ArrayList<JobListing> jobListings, Scanner c){
        System.out.println("Which listing would you like to see?");

        ArrayList<String> options = new ArrayList<>();

        for (JobListing jobListing :
                jobListings) {
            options.add(jobListing.getTitle());
        }

        int selection = selections(options, true, c);

        if (selection != -1){
            JobListing display = jobListings.get(selection);
            displayListing(display);
            if (!display.isSaved()){
                print("would you like to watch this listing?\n1: yes\n2: no");
                boolean picked = false;
                while (!picked){
                    String i2 = c.next();
                    switch(i2){
                        case "1":
                            user.addListingToWatch(display);
                            print("Listing added to " + user.getData(User.ACCOUNT_NAME) + "'s watch list");
                        case "2":
                            picked = true;
                            break;
                        default:
                            print("'" + i2 + "' is not a valid input!");
                            break;
                    }
                }
            }
        }
    }

    private static void displayListing(JobListing jobListing){
        String nl = "\n";
        String msg = "Listing Name: " + jobListing.getTitle() + "\n";
        msg += "Location: " + jobListing.getData(JobListing.LOCATION) + nl;
        msg += "Job Type: " + jobListing.getJobType() + nl;
        msg += "Pay: " + jobListing.getData(JobListing.PAY) + nl;
        msg += "Listing Date: " + jobListing.getListingDate().toString() + nl;
        msg += "Listing Description: " + jobListing.getData(JobListing.DESCRIPTION) + nl;
        msg += "Requirements: " + jobListing.getData(JobListing.REQUIREMENTS) + nl;
        msg += "Qualifications: " + jobListing.getData(JobListing.QUALIFICATIONS) + nl;
        msg += "Application Requirements: " + jobListing.getData(JobListing.REQUIREMENTS);

        print(msg);
    }

    private static void print(String msg){
        System.out.println(msg);
    }

    private static int selections(ArrayList<String> options, boolean hasCancel, Scanner c) {
        int count = options.size();
        if (hasCancel){
            options.add("Cancel");
        }

        while (true){
            for (int i = 0; i < options.size(); i++) {
                String msg = (i + 1) + ": " + options.get(i);
                System.out.println(msg);
            }
            String input = c.next();

            try{
                int select = Integer.parseInt(input) - 1;
                if (select < options.size() && select >= 0){
                    if (select == options.size() - 1){
                        return -1;
                    }
                    return select;
                }
                else{
                    System.out.println("'" + input + "' is not a valid input");
                }
            }
            catch (ClassCastException e){
                System.out.println("'" + input + "' is not a valid input");
            }
        }
    }

    private static void demo(String wrkPath) {
        try{
            String file = wrkPath + "\\test.json";
            Path path = Path.of(file);
            String jsonData = Files.readString(path);

            // this is the JSON file. Read the org.json documentation here
            // https://www.javadoc.io/doc/org.json/json/latest/index.html
            // For the most part, it's effectively the same as a Python Dict,
            // just that it's keys are always Strings, and it's values are stored
            // as Objects, and so they may need to be cast.

            JSONObject test = new JSONObject(jsonData);

            // a little for loop of the json file.

            Iterator<String> jsonKeys = test.keys();
            System.out.println(test.get("object") instanceof HashMap);

            while(jsonKeys.hasNext()){
                String currentJsonKey = jsonKeys.next();
                if (test.get(currentJsonKey) instanceof JSONObject){
                    JSONObject nestedJson = (JSONObject) test.get(currentJsonKey);

                    Iterator<String> nestedJsonKeys = nestedJson.keys();

                    while (nestedJsonKeys.hasNext()){
                        String currentNestedJsonKey = nestedJsonKeys.next();
                        System.out.print("    ");
                        System.out.println(currentNestedJsonKey + " - " + nestedJson.get(currentNestedJsonKey));

                    }

                }
                System.out.println(currentJsonKey + " - " + test.get(currentJsonKey));
            }

            // This while loop can be made a recursive method to continuously print nested JSON maps
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static String help() {

        return
                "List of available commands:\n" +
                "---------------------------\n" +
                "help: get help\n" +
                "exit: end program\n" +
                "search: search for a job entry\n" +
                "saved: view watched listings\n";
                // space here for more commands later
    }

    // to obtain the earliest allowed date for the listing
    public static LocalDateTime dateTimeInput(Scanner c) {

        LocalDateTime now = LocalDateTime.now();
        String input;

        System.out.println("Enter earliest posting date to be searched:");
        System.out.println("1: 1 day ago");
        System.out.println("2: 3 days ago");
        System.out.println("3: 7 days ago");
        System.out.println("4: 14 days ago");

        input = c.next();

        switch (input) {
            case "1":
                return now.minusDays(1);
            case "2":
                return now.minusDays(3);
            case "3":
                return now.minusDays(7);
            case "4":
                return now.minusDays(14);
            default:
                return dateTimeInput(c);
        }

    }

    public static JobType jobTypeInput(Scanner c) {

        System.out.println("Enter job type:");
        System.out.println("1: Full Time");
        System.out.println("2: Part Time");

        String input  = c.next();

        switch (input) {
            case "1":
                return JobType.FULL_TIME;
            case "2":
                return JobType.PART_TIME;
            default:
                return jobTypeInput(c);
        }

    }



}
