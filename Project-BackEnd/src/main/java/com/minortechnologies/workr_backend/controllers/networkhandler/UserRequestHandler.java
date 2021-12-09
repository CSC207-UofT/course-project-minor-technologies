package com.minortechnologies.workr_backend.controllers.networkhandler;

import com.minortechnologies.workr_backend.controllers.localcache.LocalCache;
import com.minortechnologies.workr_backend.controllers.usermanagement.AuthTokenController;
import com.minortechnologies.workr_backend.controllers.usermanagement.UserManagement;
import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.listing.ListingType;
import com.minortechnologies.workr_backend.entities.user.Experience;
import com.minortechnologies.workr_backend.entities.user.Score;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.framework.networkhandler.Application;
import com.minortechnologies.workr_backend.usecase.factories.EntryDataMapTypeCaster;
import com.minortechnologies.workr_backend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_backend.usecase.factories.userfactory.CreateUser;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;
import org.apache.commons.lang3.SerializationUtils;

import java.util.*;

public class UserRequestHandler {

    /**
     * authenticates a login and password. If there is a User Account with the matching
     * password and login, returns a token. Otherwise returns null.
     *
     * @param login A user Login
     * @param password A password to attempt to authenticate a user with
     * @return String, representing the Authentication Token if the authentication was a success, otherwise returns null.
     */
    public static String authenticateSignIn(String login, String password){
        return Application.getUserManagement().signIn(login, password, true);
    }

    /**
     * Gets all account data (except for hashed password and salt) for a specified token and login
     *
     * @param login The login to retrieve the account data
     * @param token the token associated with the login
     * @return A hashmap, containing the deserialized account data, or an error code if the operation failed.
     */
    public static HashMap<String, Object> getAccountData(String login, String token){
        HashMap<String, Object> returnMap = new HashMap<>();
        User user = authenticateAndGetUser(login, token);
        if (user == null){
            returnMap.put(NetworkResponseConstants.ERROR_KEY, NetworkResponseConstants.TOKEN_AUTH_FAIL_STRING);
            return returnMap;
        }
        returnMap = user.serialize();
        removePrivateData(returnMap);
        return returnMap;
    }

    /**
     * removes sensitive data (such as passwords, salt, etc) from a hashmap and
     * replaces the data with null keys.
     *
     * @param data the dataset to be altered.
     */
    private static void removePrivateData(HashMap<String, Object> data){
        data.put(User.SALT, null);
        data.put(User.HASHED_PASSWORD, null);
    }

    /**
     * Authenticates that a token belongs to the specified login, and that the
     * token is not expired.
     *
     * @param token the token
     * @param login the login to match to the token
     * @return whether the login matches the token
     */
    public static boolean authenticateToken(String login, String token){
        AuthTokenController controller = Application.getAuthTokenController();
        return controller.Authenticate(login, token);
    }

    /**
     * Creates a new account.
     *
     * @param username The desired username
     * @param email An email.
     * @param login The desired login
     * @param password A desired password
     * @return response code on whether the operation was successful. 1 indicates operation success.
     */
    public static int createUser(String username, String email, String login,
                                 String password){
        UserManagement um = Application.getUserManagement();

        //TODO: add username, email, and login form checks

        if (!um.createUser(username, login, email, password)){
            return NetworkResponseConstants.LOGIN_TAKEN;
        }
        return NetworkResponseConstants.OPERATION_SUCCESS;
    }

    public static int setUserData(String login, String token, String key, String data){
        if (!authenticateToken(login, token)){
            return NetworkResponseConstants.TOKEN_AUTH_FAIL;
        }
        if (!Arrays.asList(User.KEYS).contains(key)){
            return NetworkResponseConstants.KEY_NOT_EXIST;
        }

        UserManagement um = Application.getUserManagement();
        User targetUser = um.getUserByLogin(login);

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put(key, data);

        EntryDataMapTypeCaster entryDataMapTypeCaster = new EntryDataMapTypeCaster();
        try{
            entryDataMapTypeCaster.convertValueTypes(dataMap);
        }
        catch (ClassCastException e){
            return NetworkResponseConstants.PAYLOAD_MALFORMED;
        }

        targetUser.updateEntry(dataMap);
        return NetworkResponseConstants.OPERATION_SUCCESS;
    }

    public static int setUserData(String login, String token, Map<String, Object> dataMap){
        if (!authenticateToken(login, token)){
            return NetworkResponseConstants.TOKEN_AUTH_FAIL;
        }
        HashMap<String, Object> dataCopy = SerializationUtils.clone((HashMap<String, Object>)dataMap);

        (new EntryDataMapTypeCaster()).convertValueTypes(dataCopy);

        int count = EntryDataMapTypeCaster.malformedDataCount(dataCopy, dataMap);

        if (count == dataCopy.keySet().size()){
            return NetworkResponseConstants.PAYLOAD_MALFORMED;
        }

        try {
            for (String key:
                 User.KEYS) {
                if (!dataMap.containsKey(key)){
                    dataMap.put(key, null);
                }
            }

            Entry newData = new CreateUser().create(dataMap, true);

            UserManagement um = Application.getUserManagement();
            User targetUser = um.getUserByLogin(login);

            targetUser.updateEntry(newData);
            return NetworkResponseConstants.OPERATION_SUCCESS;

        } catch (MalformedDataException e) {
            e.printStackTrace();
            return NetworkResponseConstants.PAYLOAD_MALFORMED;
        }
    }

    /**
     * Gets the listings currently watched by the user.
     * @param login the login to retrieve watched listings from
     * @param token the token associated with the account
     * @return A hashmap containing the list of watched listings, or an error code if the operation was not successful.
     */
    public static HashMap<String, Object> getUserWatchedListings(String login, String token){
        HashMap<String, Object> finalMap = new HashMap<>();
        User user = authenticateAndGetUser(login, token);
        if (user == null){
            finalMap.put(NetworkResponseConstants.ERROR_KEY, NetworkResponseConstants.TOKEN_AUTH_FAIL_STRING);
            return finalMap;
        }
        
        HashSet<JobListing> watchedListingsSet = user.getWatchedListings();
        ArrayList<HashMap<String, Object>> watchedListings = new ArrayList<>();
        for (JobListing jl:
             watchedListingsSet) {
            HashMap<String, Object> jlData = jl.serialize();
            watchedListings.add(jlData);
        }

        finalMap.put("watchedListings", watchedListings);
        return finalMap;
    }

    /**
     * Gets the custom listings currently watched by the user.
     * @param login the login to retrieve watched listings from
     * @param token the token associated with the account
     * @return A hashmap containing the list of watched listings, or an error code if the operation was not successful.
     */
    public static HashMap<String, Object> getUserCustomListings(String login, String token){
        HashMap<String, Object> finalMap = new HashMap<>();
        User user = authenticateAndGetUser(login, token);
        if (user == null){
            finalMap.put(NetworkResponseConstants.ERROR_KEY, NetworkResponseConstants.TOKEN_AUTH_FAIL_STRING);
            return finalMap;
        }

        HashSet<JobListing> watchedListingsSet = user.getWatchedListings();
        ArrayList<HashMap<String, Object>> customListings = new ArrayList<>();
        for (JobListing jl:
                watchedListingsSet) {
            if (jl.getListingType() == ListingType.CUSTOM){
                HashMap<String, Object> jlData = jl.serialize();
                customListings.add(jlData);
            }
        }

        finalMap.put("customListings", customListings);
        return finalMap;
    }

    public static User authenticateAndGetUser(String login, String token){
        if (!authenticateToken(login, token)){
            return null;
        }
        AuthTokenController atc = Application.getAuthTokenController();
        return atc.retrieveUser(token, login);
    }

    public static String addToWatchedListing(String login, String token, HashMap<String, Object> listing){
        User user = authenticateAndGetUser(login, token);
        if (user == null){
            return NetworkResponseConstants.TOKEN_AUTH_FAIL_STRING;
        }
        LocalCache lc = Application.getLocalCache();
        try {
            Entry processed = ICreateEntry.createEntry(listing);

            if (processed instanceof JobListing){
                ((JobListing)processed).setSaved(true);

                Entry existing = lc.addJobListing(processed);

                // Intellij told me to do this, not entirely sure what this does.
                String listingUUID;
                listingUUID = ((JobListing) Objects.requireNonNullElse(existing, processed)).getUUID();

                user.addListingToWatch(listingUUID);
                return listingUUID;
            }
        } catch (MalformedDataException e) {
            e.printStackTrace();
        }
        return NetworkResponseConstants.PAYLOAD_MALFORMED_STRING;
    }

    public static int updatePassword(String login, String token, Map<String, String> payload) {
        User user = authenticateAndGetUser(login, token);
        if (user == null){
            return NetworkResponseConstants.TOKEN_AUTH_FAIL;
        }

        if (!payload.containsKey("oldPass") || !payload.containsKey("newPass")){
            return NetworkResponseConstants.PAYLOAD_MALFORMED;
        }

        if (Application.getUserManagement().signIn(login, payload.get("oldPass"), false) != null){
            user.changePassword(payload.get("newPass"));
            return NetworkResponseConstants.OPERATION_SUCCESS;
        }
        return NetworkResponseConstants.TOKEN_AUTH_FAIL;
    }

    private static Map<String, Object> getExperiencePackage(String login, String token, Map<String, Object> payload){

        HashMap<String, Object> expPackage = new HashMap<>();

        User user = authenticateAndGetUser(login, token);
        if (user == null){
            expPackage.put(NetworkResponseConstants.ERROR_KEY, NetworkResponseConstants.TOKEN_AUTH_FAIL);
            return expPackage;
        }
        Map<String, Object> experienceData = (Map<String, Object>) payload.get("payloadData");
        Object experienceTypeObj = payload.remove("experienceType");
        if (experienceTypeObj == null){
            expPackage.put(NetworkResponseConstants.ERROR_KEY, NetworkResponseConstants.PAYLOAD_MALFORMED);
            return expPackage;
        }
        try {
            if (experienceTypeObj instanceof String){
                String experienceType = (String) experienceTypeObj;
                switch (experienceType){
                    case User.REL_WORK_EXP:
                    case User.UREL_WORK_EXP:
                    case User.LEADERSHIP:
                        Entry entry = ICreateEntry.createEntry(experienceData);
                        if (!(entry instanceof Experience)){
                            expPackage.put(NetworkResponseConstants.ERROR_KEY, NetworkResponseConstants.INCORRECT_KEYS);
                            return expPackage;
                        }
                        ArrayList<Experience> experiences = (ArrayList<Experience>) user.getData(experienceType);
                        expPackage.put("experiences", experiences);
                        expPackage.put("entry", entry);
                        return expPackage;
                }
            }
        } catch (MalformedDataException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            e.printStackTrace();
            expPackage.put(NetworkResponseConstants.ERROR_KEY, NetworkResponseConstants.PAYLOAD_MALFORMED);
            return expPackage;
        }
        expPackage.put(NetworkResponseConstants.ERROR_KEY, NetworkResponseConstants.DATA_MALFORMED);
        return expPackage;
    }

    public static int addExperience(String login, String token, Map<String, Object> payload) {
        Map<String, Object> processedPackage = getExperiencePackage(login, token, payload);

        if (processedPackage.containsKey(NetworkResponseConstants.ERROR_KEY)){
            return (int) processedPackage.get(NetworkResponseConstants.ERROR_KEY);
        }
        ArrayList<Experience> targetList = (ArrayList<Experience>) processedPackage.get("experiences");
        Experience experienceEntry = (Experience) processedPackage.get("entry");
        targetList.add(experienceEntry);
        return NetworkResponseConstants.OPERATION_SUCCESS;
    }

    public static int removeExperience(String login, String token, Map<String, Object> payload){
        Map<String, Object> processedPackage = getExperiencePackage(login, token, payload);

        if (processedPackage.containsKey(NetworkResponseConstants.ERROR_KEY)){
            return (int) processedPackage.get(NetworkResponseConstants.ERROR_KEY);
        }
        ArrayList<Experience> targetList = (ArrayList<Experience>) processedPackage.get("experiences");
        Experience experienceEntry = (Experience) processedPackage.get("entry");
        targetList.remove(experienceEntry);
        return NetworkResponseConstants.OPERATION_SUCCESS;
    }


    public static ArrayList<Map<String, Object>> getScores(String login, String token) {
        ArrayList<Map<String, Object>> returnList = new ArrayList<>();
        User user = UserRequestHandler.authenticateAndGetUser(login, token);
        if (user == null) {
            Map<String, Object> errMap = new HashMap<>();
            errMap.put(NetworkResponseConstants.ERROR_KEY, NetworkResponseConstants.TOKEN_AUTH_FAIL_STRING);
            returnList.add(errMap);
            return returnList;
        }

        ArrayList<Score> scores = (ArrayList<Score>) user.getData(User.SCORES);
        for (Score score:
             scores) {
            returnList.add(score.serialize());
        }

        return returnList;
    }
}
