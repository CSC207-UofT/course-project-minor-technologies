package com.minortechnologies.workr_backend.networkhandler;

import com.minortechnologies.workr_backend.controllers.localcache.LocalCache;
import com.minortechnologies.workr_backend.controllers.usermanagement.AuthTokenController;
import com.minortechnologies.workr_backend.controllers.usermanagement.UserManagement;
import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.listing.ListingType;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.usecase.factories.EntryDataMapTypeCaster;
import com.minortechnologies.workr_backend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_backend.usecase.fileio.JSONSerializer;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;
import com.minortechnologies.workr_backend.usecase.security.Security;
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


    public static HashMap<String, Object> getAccountData(String login, String token){
        if (authenticateToken(login, token)){
            User user = Application.getUserManagement().getUserByLogin(login);
            HashMap<String, Object> userData = user.serialize();
            removePrivateData(userData);
            return userData;
        }
        return null;
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

        EntryDataMapTypeCaster edmtc = new EntryDataMapTypeCaster();
        try{
            edmtc.convertValueTypes(dataMap);
        }
        catch (ClassCastException e){
            return NetworkResponseConstants.PAYLOAD_MALFORMED;
        }

        targetUser.updateEntry(dataMap);
        return NetworkResponseConstants.OPERATION_SUCCESS;
    }

    public static int setUserData(String login, String token, Map<String, Object> dataMap){
        if (!authenticateToken(login, token)){
            return 0;
        }
        HashMap<String, Object> dataCopy = SerializationUtils.clone((HashMap<String, Object>)dataMap);

        (new EntryDataMapTypeCaster()).convertValueTypes(dataCopy);

        int count = EntryDataMapTypeCaster.malformedDataCount(dataCopy, dataMap);

        if (count == dataCopy.keySet().size()){
            return NetworkResponseConstants.PAYLOAD_MALFORMED;
        }

        UserManagement um = Application.getUserManagement();
        User targetUser = um.getUserByLogin(login);

        targetUser.updateEntry(dataMap);
        return 1;
    }
    
    
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
}
