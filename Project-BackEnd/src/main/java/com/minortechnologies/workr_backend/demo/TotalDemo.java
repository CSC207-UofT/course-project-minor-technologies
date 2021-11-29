package com.minortechnologies.workr_backend.demo;

import com.minortechnologies.workr_backend.controllers.backgroundoperations.BackgroundOperations;
import com.minortechnologies.workr_backend.controllers.localcache.LocalCache;
import com.minortechnologies.workr_backend.controllers.usermanagement.UserManagement;
import com.minortechnologies.workr_backend.demo.demosource.DemoJobListingSource;
import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.listing.JobType;
import com.minortechnologies.workr_backend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.main.Main;
import com.minortechnologies.workr_backend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;

import java.time.LocalDateTime;
import java.util.*;

public class TotalDemo {

    private static LocalCache localCache;

    public static UserManagement getUserManagement() {
        return userManagement;
    }

    public static void setUserManagement(UserManagement userManagement) {
        TotalDemo.userManagement = userManagement;
    }

    private static UserManagement userManagement;

    public static void setLocalCache(LocalCache localCache) {
        TotalDemo.localCache = localCache;
    }

    public static LocalCache getLocalCache(){
        return localCache;
    }

    public static DemoJobListingSource demoSource;

    private static boolean initiated = false;

    public static boolean getInitiated(){
        return initiated;
    }

    public static void main(String[] args) {

        initiated = true;

        Main.init();
        JayWangDemoScratchClass.init();

        demoSource = new DemoJobListingSource();

        localCache = Main.getLocalCache();

        userManagement = Main.getUserManagement();

        localCache.loadSavedListings();

        Scanner c = new Scanner(System.in); // creating console scanner

        BackgroundOperations.startBackgroundLoop();

        label:
        while(true) {

            System.out.println("What would you like to do?\nType \"help\" for commands.");
            String command = c.nextLine().toLowerCase(Locale.ROOT);

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

                    ArrayList<Map<String, Object>> results = demoSource.search(query);

                    ArrayList<JobListing> toDisplay = new ArrayList<>();

                    for (Map<String, Object> listingData:
                         results) {
                        try{
                            JobListing listing = (JobListing) ICreateEntry.createEntry(listingData, false);
                            toDisplay.add(listing);
                        }
                        catch (MalformedDataException e){
                            e.printStackTrace();
                        }
                    }

                    System.out.println("Relevant listings:");

                    // printing all relevant listings
                    for (JobListing jobListing : toDisplay) {
                        System.out.println(jobListing.getData(JobListing.TITLE) + ", " + jobListing.getData(JobListing.LOCATION));
                    }

                    viewListing(toDisplay, c);
                    break;
                case "am":
                case "account management":
                    JayWangDemoScratchClass.accountDemo(c);
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
        userManagement.saveUsers();
        localCache.saveAllListings();
        System.out.println("End of demo");

        // demo(com.minortechnologies.workr.Framework.FileIO.FileIO.WORK_PATH);
    }

    public static void viewListing(ArrayList<JobListing> jobListings, Scanner c){
        System.out.println("Which listing would you like to see?");

        ArrayList<String> options = new ArrayList<>();

        for (JobListing jobListing :
                jobListings) {
            options.add(jobListing.getTitle());
        }

        int selection = selections(options, true, c);

        if (selection != -1 && jobListings.size() != 0){
            JobListing display = jobListings.get(selection);
            displayListing(display);
            if (!display.isSaved() && getUserManagement().getCurrentActiveUser() != null){
                User user = getUserManagement().getCurrentActiveUser();
                print("would you like to watch this listing?\n1: yes\n2: no");
                boolean picked = false;
                while (!picked){
                    String i2 = c.next();
                    switch(i2){
                        case "1":
                            Entry old = localCache.addJobListing(display);
                            display = old == null ? display : (JobListing) old;
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

    public static int selections(ArrayList<String> options, boolean hasCancel, Scanner c) {
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
                    if (hasCancel && select == options.size() - 1){
                        options.remove(options.size() -1);
                        return -1;
                    }
                    options.remove(options.size() -1);
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

    public static String help() {

        return
                "List of available commands:\n" +
                "---------------------------\n" +
                "help: get help\n" +
                "exit: end program\n" +
                "search: search for a job entry\n" +
                "account management: access account management\n";
                // space here for more commands later
    }

    // to obtain the earliest allowed date for the listing
    public static LocalDateTime dateTimeInput(Scanner c) {

        LocalDateTime now = LocalDateTime.now();
        String input;

        System.out.println("Enter earliest posting date to be searched:");
        System.out.println("1: 14 day ago");
        System.out.println("2: 1 month");
        System.out.println("3: 6 months");
        System.out.println("4: 1 year");

        input = c.next();

        switch (input) {
            case "1":
                return now.minusDays(14);
            case "2":
                return now.minusMonths(1);
            case "3":
                return now.minusMonths(6);
            case "4":
                return now.minusYears(1);
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
