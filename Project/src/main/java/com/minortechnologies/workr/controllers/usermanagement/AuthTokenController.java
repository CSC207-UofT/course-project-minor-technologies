package com.minortechnologies.workr.controllers.usermanagement;

import com.minortechnologies.workr.entities.security.AuthToken;
import com.minortechnologies.workr.entities.user.User;
import com.minortechnologies.workr.framework.fileio.FileIO;
import com.minortechnologies.workr.usecase.fileio.JSONSerializer;
import com.minortechnologies.workr.usecase.security.AuthTokenDB;
import org.apache.el.parser.Token;
import org.json.JSONObject;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthTokenController {

    private final AuthTokenDB tokenDB;

    public AuthTokenDB getTokenDB(){
        return tokenDB;
    }

    public AuthTokenController(Iterable<AuthToken> tokens){
        this.tokenDB = new AuthTokenDB(tokens);
    }

    public AuthTokenController(UserManagement userManagement){
        this.tokenDB = new AuthTokenDB();
        if (userManagement != null){
            loadTokens(userManagement);
        }
    }

    /**
     * Authenticates that a token belongs to the specified login, and that the
     * token is not expired.
     *
     * If the token is found to be valid but expired, removes the token from the
     * database.
     *
     * If the token is a valid, non-expired token, and matches the login, the token
     * is refreshed for 15 days.
     *
     * @param token the token
     * @param login the login to match to the token
     * @return whether the login matches the token
     */
    public boolean Authenticate(String token, String login){
        AuthToken token1 = tokenDB.getToken(token);

        if (token1 != null){
            if (token1.getExpirationDate().isBefore(LocalDate.now()) || token1.getCreationDate().plusYears(1).isBefore(LocalDate.now())){
                tokenDB.removeToken(token);
            }
            else{
                String tokenAssociatedUser = (String) token1.getAssociatedUser().getData(User.LOGIN);
                if (tokenAssociatedUser.equals(login)){
                    token1.refreshToken();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Generates a token for the specified user.
     *
     * @param user the user for the token to be generated
     * @return a Token for the specified user.
     */
    public AuthToken generateToken(User user){
        AuthToken token = new AuthToken(user);
        tokenDB.addToken(token);
        return token;
    }

    /**
     * Loads saved tokens.
     *
     * @param userManagement a userManagement instance to retrieve associated users.
     */
    public void loadTokens(UserManagement userManagement){
        ArrayList<String> files = FileIO.getFileNamesInDir(FileIO.TOKENS);
        if (files.contains("tokens.json")){
            String tokens = FileIO.readFile(FileIO.TOKENS + File.separator + "tokens.json");
            JSONObject tokensJson = new JSONObject(tokens);

            for (String key:
                 tokensJson.keySet()) {
                JSONObject tokenData = tokensJson.getJSONObject(key);
                String token = tokenData.getString(AuthToken.TOKEN);
                LocalDate creationDate = LocalDate.parse(tokenData.getString(AuthToken.CREATION_DATE));
                LocalDate expirationDate = LocalDate.parse(tokenData.getString(AuthToken.EXPIRATION_DATE));
                String userLogin = tokenData.getString(AuthToken.ASSOCIATED_USER);
                User user = userManagement.getUserByLogin(userLogin);

                AuthToken tokenObject = new AuthToken(token, user, creationDate, expirationDate);
                tokenDB.addToken(tokenObject);
            }
        }
    }

    /**
     * Serializes the tokens.
     */
    public void saveTokens(){
        Map<String, Map<String, String>> tokensData = new HashMap<>();
        for (AuthToken token:
             tokenDB) {
            tokensData.put(token.getToken(), token.serialize());
        }
        JSONObject tokensJSON = new JSONObject(tokensData);
        String dataString = tokensJSON.toString();

        FileIO.WriteFile(FileIO.TOKENS, "tokens.json", dataString);
    }
}
