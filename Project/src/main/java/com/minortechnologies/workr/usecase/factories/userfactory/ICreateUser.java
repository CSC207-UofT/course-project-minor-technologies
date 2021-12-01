package com.minortechnologies.workr.usecase.factories.userfactory;

import com.minortechnologies.workr.entities.user.User;
import com.minortechnologies.workr.usecase.factories.ICreateEntry;
import com.minortechnologies.workr.usecase.fileio.MalformedDataException;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.Map;

public interface ICreateUser extends ICreateEntry {
    User create(Map<String, Object> UserDataMap) throws MalformedDataException;

    User create(String username, String login, String password);

    static @NotNull
    ArrayList<String> verifyMapIntegrityStatic(Map<String, Object> userDataMap){
        throw new UnsupportedOperationException();
    }


}
