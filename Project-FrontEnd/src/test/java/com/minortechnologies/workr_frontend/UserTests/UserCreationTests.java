package com.minortechnologies.workr_frontend.UserTests;

import com.minortechnologies.workr_frontend.entities.user.User;
import com.minortechnologies.workr_frontend.usecase.security.Security;
import com.minortechnologies.workr_frontend.usecase.factories.userfactory.CreateUser;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserCreationTests {

    String demoLogin;
    String demoToken;
    String demoName;

    @Before
    public void setUp(){
        demoName = "DemoName";
        demoLogin = "DemoLogin";
        demoToken = "somegenerictokenthingy";
    }

    /**
     * Tests that CreateUser correctly instantiates a User Object.
     *
     * Also tests User.matchPassword();
     */
    @Test
    public void testCreateUserUserCreation(){
        User user = new CreateUser().create(demoName, demoLogin, demoToken);

        assertEquals(user.getData(User.ACCOUNT_NAME), demoName);
        assertEquals(user.getData(User.LOGIN), demoLogin);
    }
}
