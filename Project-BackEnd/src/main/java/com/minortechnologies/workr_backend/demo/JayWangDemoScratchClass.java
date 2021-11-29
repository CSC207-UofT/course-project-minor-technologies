/*
 * This is a scratch file that exists purely for debugging and internal demos.
 *
 * The contents of this file does not affect the rest operation of the rest of the program in any way.
 *
 */

package com.minortechnologies.workr_backend.demo;

import com.minortechnologies.workr_backend.controllers.dataprocessing.DataFormat;
import com.minortechnologies.workr_backend.controllers.localcache.LocalCache;
import com.minortechnologies.workr_backend.controllers.usermanagement.UserManagement;
import com.minortechnologies.workr_backend.demo.demosource.DemoJobListingSource;
import com.minortechnologies.workr_backend.demo.demosource.DemoSourceJobListing;
import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.listing.JobType;
import com.minortechnologies.workr_backend.entities.searchquery.SearchQuery;
import com.minortechnologies.workr_backend.entities.user.Experience;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.framework.fileio.FileIO;
import com.minortechnologies.workr_backend.usecase.fileio.IEntrySerializer;
import com.minortechnologies.workr_backend.usecase.fileio.JSONSerializer;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;
import com.minortechnologies.workr_backend.usecase.user.UserDB;
import com.minortechnologies.workr_backend.main.Main;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A little demo for writing to and from a json file, and loading it as an com.minortechnologies.workr.Entities.Listings.Listing Object.
 *
 */
public class JayWangDemoScratchClass {

    private static LocalCache localCache;
    private static SearchQuery nestedDemo;
    private static Experience expDemo;
    private static DemoJobListingSource demoDB;
    private static UserManagement um;

    public static void init(){
        localCache = Main.getLocalCache();
    }


    public static void main(String[] args) {

        System.out.println("Go Minor Technologies!");

        formatDemoListings();

        demoDB = new DemoJobListingSource();

        nestedDemo = new SearchQuery("demo", "Toronto", LocalDateTime.now(), JobType.FULL_TIME);
        expDemo = new Experience();
        expDemo.addData(Experience.EXPERIENCE_TITLE, "DemoTitle");
        ArrayList<String> expDesc = new ArrayList<>();
        expDesc.add("Desc1");
        expDesc.add("Desc2");
        expDemo.addData(Experience.EXPERIENCE_DESCRPTION, expDesc);
        expDemo.addData(Experience.START_TIME, LocalDateTime.MIN);
        expDemo.addData(Experience.END_TIME, LocalDateTime.now());

        localCache = new LocalCache();
        UserManagement um = new UserManagement();
        JayWangDemoScratchClass.um = um;
        TotalDemo.setLocalCache(localCache);
        TotalDemo.setUserManagement(um);

        accountDemo(null);

        localCache.loadSavedListings();


        System.out.println("ended");
    }

    /**
     * reads all files (that's not named "ListingTemplate.json") from \ListingInOut\Load and
     * saves it in \ListingInOut\Save for UID, Hashing, and Filename generation
     *
     */
    public static void formatDemoListings(){

        String base = File.separator + "ListingInOut" + File.separator;

        String load = base + "Load";
        String save = base + "Save";
        ArrayList<String> files = FileIO.getFileNamesInDir(load, ".json");
        IEntrySerializer serializer = new JSONSerializer();
        for (String file : files) {
            if (!(file.equals("ListingTemplate.json"))){
                try {
                    Entry jobListing = DataFormat.createEntry(FileIO.readFile(load + File.separator + file));
                    assert jobListing instanceof JobListing;

                    String listingData = serializer.serialize(jobListing.serialize());

                    FileIO.WriteFile(save, "entry_" + jobListing.getData(DemoSourceJobListing.DEMO_SOURCE_ID) + ".json", listingData);
                } catch (MalformedDataException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void accountDemo(Scanner c){
        if (c == null){
            c = new Scanner(System.in);
        }
        boolean running = true;
        while (running){
            System.out.println("What would you like to do?");
            System.out.println("1: Create Account");
            System.out.println("2: Sign in");
            System.out.println("3: Exit");
            System.out.println("4: print user list & details");
            System.out.println("5: Access Account Data");

            String choice = c.nextLine();

            switch (choice){
                case "1":
                    createAccountDemo(c);
                    break;
                case "2":
                    loginDemo(c);
                    break;
                case "3":
                    running = false;
                    break;
                case "4":
                    printUserDeets();
                    break;
                case "5":
                    accessUserData(c);
                default:
                    System.out.println("not good choose");
                    break;
            }
        }
        UserDB userDB = TotalDemo.getUserManagement().getUserDatabase();

        if (nestedDemo != null){
            for (Entry entry:
                    userDB) {
                ArrayList<Experience> leads = (ArrayList<Experience>) entry.getData(User.LEADERSHIP);
                ArrayList<Experience> rel = (ArrayList<Experience>) entry.getData(User.REL_WORK_EXP);
                ArrayList<Experience> urel = (ArrayList<Experience>) entry.getData(User.UREL_WORK_EXP);
                HashSet<SearchQuery> sQs = (HashSet<SearchQuery>) entry.getData(User.WATCHED_SEARCH_QUERIES);
//                leads.add(expDemo);
//                rel.add(expDemo);
//                urel.add(expDemo);
                sQs.add(nestedDemo);
            }
        }
        TotalDemo.getUserManagement().saveUsers();
    }

    private static void accessUserData(Scanner c){
        if (TotalDemo.getUserManagement().getCurrentActiveUser() == null){
            System.out.println("You are not currently signed in, please sign in");
            return;
        }
        ArrayList<String> opts = new ArrayList<>(Arrays.asList("view account details", "view saved listings", "change username", "change password"));
        while (true){
            int choice = TotalDemo.selections(opts, true, c);
            switch (choice){
                case -1:
                    return;
                case 0:
                    printUserDeets();
                    break;
                case 1:
                    viewSavedListings(c);
                    break;
                case 2:
                    changeUserName();
                    break;
                case 3:
                    changePassword();
                    break;
            }
        }
    }

    private static void changePassword(){
    }

    private static void changeUserName(){
    }

    private static void viewSavedListings(Scanner c){
        ArrayList<JobListing> jobListings = new ArrayList<>();
        for (JobListing jobListing :
                TotalDemo.getUserManagement().getCurrentActiveUser().getWatchedListings()) {
            jobListings.add(localCache.getListingFromUUID(jobListing.getUUID()));
        }
        TotalDemo.viewListing(jobListings, c);
    }


    private static void loginDemo(Scanner c){
        while (true){
            System.out.println("Enter your login");
            String login = c.nextLine();
            System.out.println("Enter your password");
            String pass = c.nextLine();

            if (TotalDemo.getUserManagement().signIn(login, pass, true) != null){
                System.out.println("Successfully signed in,");
                User user = TotalDemo.getUserManagement().getUserByLogin(login);
                System.out.println("Welcome, " + user.getData(User.ACCOUNT_NAME));

                break;
            }
            else{
                System.out.println("Username or Password does not match any login details");
                System.out.println("type 'exit' to leave");
                String choice = c.nextLine();
                if (Objects.equals(choice, "exit")){
                    break;
                }
            }
        }
    }

    private static void printUserDeets(){
        UserDB udb = TotalDemo.getUserManagement().getUserDatabase();

        for (Entry entry:
             udb) {
            User user = (User) entry;

            System.out.println("============================================");
            System.out.println("Account Name: {" + user.getData(User.ACCOUNT_NAME) + "}");
            System.out.println("Account Login: {" + user.getData(User.LOGIN) + "}");
            System.out.println("Account Salt: {" + user.getData(User.SALT) + "}");
            System.out.println("Account Password: {" + user.getData(User.HASHED_PASSWORD) + "}");
        }
    }

    private static void createAccountDemo(Scanner c){
        while (true){

            System.out.println("What is your name?");
            String name = c.nextLine();

            String login;

            boolean validLogin = false;
            do {
                System.out.println("Enter a login!");
                login = c.nextLine();
                if (login.length() == 0){
                    System.out.println("This is not a valid login!");
                }
                else if (!TotalDemo.getUserManagement().containsLogin(login)){
                    validLogin = true;
                }
                else{
                    System.out.println("'" + login + "' is already taken, please use a different login");
                }
            } while (!validLogin);

            System.out.println("Enter a password:");

            String pass = c.nextLine();


            System.out.println("This will be your account details:");
            System.out.println("Name: " + name + ", login: " + login + ", Password: " + pass);
            System.out.println("Would you like to change anything? (y/n)");

            String choice = c.nextLine();

            if (Objects.equals(choice, "n")){
                TotalDemo.getUserManagement().createUser(name, login, "demoEmail",pass);
                break;
            }
        }
    }
}
