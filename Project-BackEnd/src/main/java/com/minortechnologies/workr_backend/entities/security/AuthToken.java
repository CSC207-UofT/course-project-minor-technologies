package com.minortechnologies.workr_backend.entities.security;

import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.usecase.security.Security;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * An Authentication Token. Used for any User Data interactions between the frontend
 * and backend.
 *
 * Tokens will automatically expire if not used for more than 15 days (or another
 * specified amount of time), and will expire 1 year from the day of creation.
 *
 */
public class AuthToken {

    public static final String TOKEN = "token";
    public static final String CREATION_DATE = "creationDate";
    public static final String EXPIRATION_DATE = "expirationDate";
    public static final String ASSOCIATED_USER = "associatedUser";
    public static final String[] KEYS = new String[] {TOKEN, CREATION_DATE, EXPIRATION_DATE, ASSOCIATED_USER};

    private final String token;
    private final LocalDate creationDate;
    private LocalDate expirationDate;
    private final User associatedUser;


    public User getAssociatedUser(){
        return associatedUser;
    }

    public String getToken(){
        return token;
    }

    public LocalDate getExpirationDate(){
        return expirationDate;
    }

    public LocalDate getCreationDate(){
        return creationDate;
    }

    public AuthToken(String token, User associatedUser, LocalDate creationDate, LocalDate tokenExpirationDate){
        this.token = token;
        this.associatedUser = associatedUser;
        this.creationDate = creationDate;
        this.expirationDate = tokenExpirationDate;
    }

    public AuthToken(User associatedUser){
        this(associatedUser, 15);
    }

    public AuthToken(User associatedUser, int duration){
        this.associatedUser = associatedUser;
        token = Security.generateNewToken();
        creationDate = LocalDate.now();
        expirationDate = LocalDate.now().plusDays(duration);
    }

    /**
     * Refreshes this token, allows it to be used for another 15 days from the date of refresh.
     * if the token is not already expired.
     *
     * @return whether the token has been successfully refreshed.
     */
    public boolean refreshToken(){
        return refreshToken(15);
    }

    /**
     * refreshes this token, setting the expiration to be the current day + the extension.
     * if the token is not already expired or the refreshed date would be before the original
     * expiration date.
     *
     * @param extension the number of days from the current day the new expiration date should be.
     * @return whether the token has been successfully refreshed.
     */
    public boolean refreshToken(int extension){
        if (LocalDate.now().isBefore(expirationDate)){
            LocalDate newExpiration = LocalDate.now().plusDays(extension);
            if (newExpiration.isAfter(expirationDate)){
                expirationDate = LocalDate.now().plusDays(extension);
                return true;
            }
        }
        return false;
    }

    public Map<String, String> serialize(){
        HashMap<String, String> data = new HashMap<>();

        data.put(TOKEN, token);
        data.put(ASSOCIATED_USER, (String) associatedUser.getData(User.LOGIN));
        data.put(CREATION_DATE, creationDate.toString());
        data.put(EXPIRATION_DATE, expirationDate.toString());

        return data;
    }
}
