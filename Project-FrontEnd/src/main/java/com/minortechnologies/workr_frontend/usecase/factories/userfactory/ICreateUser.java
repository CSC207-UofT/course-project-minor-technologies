package com.minortechnologies.workr_frontend.usecase.factories.userfactory;

import com.minortechnologies.workr_frontend.entities.user.User;
import com.minortechnologies.workr_frontend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_frontend.usecase.fileio.MalformedDataException;


import java.util.ArrayList;
import java.util.Map;

public interface ICreateUser extends ICreateEntry {
    User create(Map<String, Object> UserDataMap) throws MalformedDataException;

    User create(String username, String login, String password);

    static ArrayList<String> verifyMapIntegrityStatic(Map<String, Object> userDataMap){
        throw new UnsupportedOperationException();
    }


}
