package com.minortechnologies.workr_backend.UserTests;

import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.usecase.security.Security;
import com.minortechnologies.workr_backend.usecase.factories.userfactory.CreateUser;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserCreationTests {

    String demoLogin;
    String demoPassword;
    String demoName;
    String demoEmail;

    @Before
    public void setUp(){
        demoName = "DemoName";
        demoLogin = "DemoLogin";
        demoPassword = "DemoPass";
        demoEmail = "email@email.com";
    }

    /**
     * Tests that CreateUser does not create the user with the password in plaintext.
     *
     * CreateUser hashes the input password and then creates a User Instance using it as opposed to saving the plaintext
     * password.
     *
     */
    @Test
    public void testUserNotPlaintextPass(){
        User user = new CreateUser().create(demoName, demoLogin, demoEmail, demoPassword);

        assertNotEquals(user.getData(User.HASHED_PASSWORD), demoPassword);
    }

    /**
     * Tests that CreateUser correctly instantiates a User Object.
     *
     * Also tests User.matchPassword();
     */
    @Test
    public void testCreateUserUserCreation(){
        User user = new CreateUser().create(demoName, demoLogin, demoEmail, demoPassword);

        byte[] salt = Security.fromHex(user.getSalt());
        String hashedPass = Security.toHex(Security.generateHash(demoPassword, salt));

        assertEquals(user.getData(User.ACCOUNT_NAME), demoName);
        assertEquals(user.getData(User.LOGIN), demoLogin);
        assertEquals(user.getData(User.HASHED_PASSWORD), hashedPass);
        assertEquals(user.getData(User.EMAIL), demoEmail);
        assertTrue(user.matchPassword(hashedPass));
    }
}
