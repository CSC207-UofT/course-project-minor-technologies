package com.minortechnologies.workr_backend.networkhandler;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserRequestReceiver {


    public static final String ACCOUNT_NAME = "accountName";
    public static final String PASSWORD = "password";
    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    private static final String[] KEYS = new String[] {ACCOUNT_NAME, PASSWORD, LOGIN, EMAIL};

    @GetMapping("/User/SignIn")
    public String signIn(@RequestParam String login, @RequestParam String password){
        return UserRequestHandler.authenticateSignIn(login, password);
    }

    @GetMapping("/User/SignInByToken")
    public boolean signInByToken(@RequestParam String login, @RequestParam String token){
        return UserRequestHandler.authenticateToken(login, token);
    }

    @PostMapping("/User/Create")
    public int createUser(@RequestBody Map<String, String> payload){

        for (String key:
             KEYS) {
            if (!payload.containsKey(key)){
                return 7;
            }
        }
        String username = payload.get("username");
        String email = payload.get("email");
        String login = payload.get("login");
        String password = payload.get("password");
        return UserRequestHandler.createUser(username, email, login, password);
    }

    @PostMapping("User/{login}/SetData")
    public int setUserData(@PathVariable String login, @RequestParam String token, @RequestBody String[] payload){
        return UserRequestHandler.setUserData(login, token, payload[0], payload[1]);
    }

    @PostMapping("User/{login}/SetDataLarge")
    public int setUserData(@PathVariable String login, @RequestParam String token, @RequestBody HashMap<String, Object> payload){
        return UserRequestHandler.setUserData(login, token, payload);
    }

    @PostMapping("/User/{login}/GetAllUserData")
    public HashMap<String, Object> getAllUserData(@PathVariable String login, @RequestParam String token){
        return UserRequestHandler.getAccountData(login, token);
    }

    @PostMapping("/User/{login}/GetWatchedListings")
    public HashMap<String, Object> getWatchedListings(@PathVariable String login, @RequestParam String token){
        return UserRequestHandler.getUserWatchedListings(login, token);
    }

    @PostMapping("/User/{login}/GetCustomListings")
    public HashMap<String, Object> getCustomListings(@PathVariable String login, @RequestParam String token){
        return UserRequestHandler.getUserCustomListings(login, token);
    }

    @PostMapping("/User/{login}/AddToWatched")
    public String addToWatchedListing(@PathVariable String login, @RequestParam String token, @RequestBody HashMap<String, Object> payload){
        return UserRequestHandler.addToWatchedListing(login, token, payload);
    }

    @PostMapping("/User/{login}/ChangePassword")
    public int changePassword(@PathVariable String login, @RequestParam String token, @RequestBody Map<String, String> payload){
        return UserRequestHandler.updatePassword(login, token, payload);
    }

    @PostMapping("/User/{login}/Experience/Add")
    public int addExperience(@PathVariable String login, @RequestParam String token, @RequestBody Map<String, Object> payload){
        return UserRequestHandler.addExperience(login, token, payload);
    }

    @PostMapping("User/{login}/Experience/Remove")
    public int removeExperience(@PathVariable String login, @RequestParam String token, @RequestBody Map<String, Object> payload){
        return UserRequestHandler.removeExperience(login, token, payload);
    }

    @GetMapping("User/{login}/Scores")
    public ArrayList<Map<String, Object>> removeExperience(@PathVariable String login, @RequestParam String token){
        return UserRequestHandler.getScores(login, token);
    }
}
