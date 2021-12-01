package com.minortechnologies.workr_backend.usecase.security;

import com.minortechnologies.workr_backend.entities.security.AuthToken;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AuthTokenDB implements Iterable<AuthToken> {

    private final HashMap<String, AuthToken> tokenHashMap;

    public AuthTokenDB(){
        tokenHashMap = new HashMap<>();
    }

    public AuthTokenDB(Iterable<AuthToken> tokens){
        this();
        for (AuthToken token:
             tokens) {
            addToken(token);
        }
    }

    private List<AuthToken> getTokensAsList(){
        return new ArrayList<>(tokenHashMap.values());
    }

    /**
     * adds a token to the database. Returns false if the token already exists.
     *
     * @param token the token to be added to the database.
     * @return whether the token was successfully added to the database.
     */
    public boolean addToken(AuthToken token){
        if (!tokenHashMap.containsKey(token.getToken())){
            tokenHashMap.put(token.getToken(), token);
            return true;
        }
        return false;
    }

    /**
     * retrieves a token from the database.
     *
     * @param token the token with the matching token String
     * @return the Token if it exists, null otherwise.
     */
    public AuthToken getToken(String token){
        return tokenHashMap.get(token);
    }

    /**
     * removes a specified token from this database.
     *
     * @param token the token to be removed
     */
    public void removeToken(String token){
        tokenHashMap.remove(token);
    }

    @NotNull
    @Override
    public Iterator<AuthToken> iterator() {
        return new AuthTokenDBIterator((ArrayList<AuthToken>) getTokensAsList());
    }

    static class AuthTokenDBIterator implements Iterator<AuthToken>{

        private final Iterator<AuthToken> toIterate;

        public AuthTokenDBIterator(ArrayList<AuthToken> tokenList){
            toIterate = tokenList.iterator();
        }

        @Override
        public boolean hasNext() {
            return toIterate.hasNext();
        }

        @Override
        public AuthToken next() {
            return toIterate.next();
        }
    }
}
