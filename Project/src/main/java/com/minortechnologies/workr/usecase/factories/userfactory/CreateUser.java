package com.minortechnologies.workr.usecase.factories.userfactory;

import com.minortechnologies.workr.entities.Entry;
import com.minortechnologies.workr.entities.user.User;
import com.minortechnologies.workr.usecase.factories.ICreateEntry;
import com.minortechnologies.workr.usecase.fileio.MalformedDataException;
import com.minortechnologies.workr.usecase.security.Security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CreateUser implements ICreateUser{

    @Override
    public User create(Map<String, Object> userDataMap) throws MalformedDataException {
        ArrayList<String> missingKeys = verifyMapIntegrity(userDataMap);
        if (missingKeys.size() != 0){
            String message = ICreateEntry.missingKeyInfo(missingKeys, "User");
            throw new MalformedDataException(message);
        }

        ArrayList<HashMap<String, Object>> queriesDataMap =
                (ArrayList<HashMap<String, Object>>) userDataMap.get(User.WATCHED_SEARCH_QUERIES);
        HashSet<Entry> queries = getDeserializedQueries(queriesDataMap);

        ArrayList<HashMap<String, Object>> relExp =
                (ArrayList<HashMap<String, Object>>) userDataMap.get(User.REL_WORK_EXP);
        ArrayList<Entry> relWorkExp = getDeserializedExperiences(relExp);

        ArrayList<HashMap<String, Object>> urelExp =
                (ArrayList<HashMap<String, Object>>) userDataMap.get(User.UREL_WORK_EXP);
        ArrayList<Entry> urelWorkExp = getDeserializedExperiences(urelExp);

        ArrayList<HashMap<String, Object>> lead =
                (ArrayList<HashMap<String, Object>>) userDataMap.get(User.LEADERSHIP);
        ArrayList<Entry> leadership = getDeserializedExperiences(lead);

        userDataMap.replace(User.REL_WORK_EXP, relWorkExp);
        userDataMap.replace(User.UREL_WORK_EXP, urelWorkExp);
        userDataMap.replace(User.LEADERSHIP, leadership);
        userDataMap.replace(User.WATCHED_SEARCH_QUERIES, queries);
        User user = new User();
        user.deserialize(userDataMap);
        return user;
    }

    private ArrayList<Entry> getDeserializedExperiences(ArrayList<HashMap<String, Object>> experiences){
        ArrayList<Entry> experienceList = new ArrayList<>();
        for (HashMap<String, Object> exps:
             experiences) {
            try {
                Entry exp = ICreateEntry.createEntry(exps);
                experienceList.add(exp);
            } catch (MalformedDataException e) {
                e.printStackTrace();
            }
        }
        return experienceList;
    }

    private HashSet<Entry> getDeserializedQueries(ArrayList<HashMap<String, Object>> queryData){
        HashSet<Entry> queries = new HashSet<>();
        for (HashMap<String, Object> query:
             queryData) {
            try {
                Entry entry = ICreateEntry.createEntry(query);
                queries.add(entry);
            } catch (MalformedDataException e) {
                e.printStackTrace();
            }
        }
        return queries;
    }

    @Override
    public User create(String username, String login, String password) {
        byte[] saltArr = Security.generateSalt();
        String salt = Security.toHex(saltArr);
        String hashedPassword = Security.toHex(Security.generateHash(password, saltArr));

        return new User(username, login, hashedPassword, salt);
    }

    @Override
    public ArrayList<String> verifyMapIntegrity(Map<String, Object> entryDataMap) {
        ArrayList<String> missingKeys = new ArrayList<>();
        for (String key:
             User.KEYS) {
            if (!entryDataMap.containsKey(key)){
                missingKeys.add(key);
            }
        }
        return missingKeys;
    }
}
