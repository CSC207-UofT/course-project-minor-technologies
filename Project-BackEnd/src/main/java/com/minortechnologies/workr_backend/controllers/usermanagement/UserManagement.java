package com.minortechnologies.workr_backend.controllers.usermanagement;

import com.minortechnologies.workr_backend.controllers.dataprocessing.DataFormat;
import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.security.AuthToken;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.framework.fileio.FileIO;
import com.minortechnologies.workr_backend.framework.networkhandler.Application;
import com.minortechnologies.workr_backend.usecase.fileio.IEntrySerializer;
import com.minortechnologies.workr_backend.usecase.fileio.JSONSerializer;
import com.minortechnologies.workr_backend.usecase.factories.userfactory.CreateUser;
import com.minortechnologies.workr_backend.usecase.user.UserDB;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class UserManagement {

    public UserDB getUserDatabase() {
        return userDatabase;
    }

    private final UserDB userDatabase;

    private User currentActiveUser;

    public UserManagement(Iterable<User> users){
        this.userDatabase = new UserDB(users);
        this.currentActiveUser = null;
    }

    public UserManagement(){
        this.userDatabase = new UserDB();
        this.currentActiveUser = null;
        loadUsers();
    }

    public void loadUsers(){
        ArrayList<Entry> entries = DataFormat.loadEntiresFromDirectorySub(FileIO.USERS);

        userDatabase.addEntries(entries);
    }

    public User getCurrentActiveUser() {
        return currentActiveUser;
    }

    public String signIn(String login, String password, boolean generateToken){
        User user = userDatabase.signIn(login, password);

        if (user != null){
            if (!generateToken || Application.getAuthTokenController() == null){
                currentActiveUser = user;
                return "TokenTemp";
                // TODO: Remove Demo Code
            }
            else{
                AuthToken token = Application.getAuthTokenController().generateToken(user);
                return token.getToken();
            }
        }
        return null;
    }

    public User getUserByLogin(String login){
        return userDatabase.getUserByLogin(login);
    }


    /**
     * Saves all listings that are being watched by the user.
     *
     */
    public void saveWatchedListings(){
        IEntrySerializer serializer = new JSONSerializer();
        HashSet<Entry> savedEntries = new HashSet<>();
        for (Entry user:
             userDatabase) {
            Object wl = user.getData(User.WATCHED_JOB_LISTINGS);
            if (wl instanceof ArrayList){
                ArrayList<?> watchedListings = (ArrayList<?>) wl;
                for (Object listElement:
                     watchedListings) {
                    Entry entry = entryCastCheck(listElement, savedEntries);
                    if (entry != null){
                        String data = serializer.serialize(entry.serialize());
                        String saveName = "entry_" + entry.getSerializedFileName() + serializer.serializerExtension();
                        FileIO.WriteFile(FileIO.SAVED_LISTINGS, saveName, data);
                        savedEntries.add(entry);
                    }
                }
            }
        }
    }

    private Entry entryCastCheck(Object item, HashSet<Entry> collisionSet){
        if (item instanceof JobListing){
            Entry entry = (Entry) item;
            if (!collisionSet.contains(entry)){
                return entry;
            }
        }
        return null;
    }

    public boolean createUser(String username, String login, String email, String password){
        User newUser = new CreateUser().create(username, login, email, password);

        return userDatabase.addEntry(newUser);
    }

    public void saveUsers(){

        IEntrySerializer serializer = new JSONSerializer();

        saveUsers(serializer);
    }

    public void saveUsers(IEntrySerializer serializer){
        if (!FileIO.containsFolder(FileIO.USERS)){
            FileIO.createDirectory(FileIO.USERS);
        }
        for (Entry entry:
             userDatabase) {
            String userPath = FileIO.USERS + File.separator + entry.getSerializedFileName();
            if (!FileIO.containsFolder(userPath)){
                FileIO.createDirectory(userPath);
            }
            String data = serializer.serialize(entry.serialize());
            String saveName = "entry_"+ entry.getSerializedFileName() + serializer.serializerExtension();
            FileIO.WriteFile(userPath, saveName, data);
        }
    }

    public boolean containsLogin(String login){
        return userDatabase.contains(login);
    }
}
