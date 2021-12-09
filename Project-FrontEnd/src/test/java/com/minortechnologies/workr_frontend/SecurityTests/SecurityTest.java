package com.minortechnologies.workr_frontend.SecurityTests;

import com.minortechnologies.workr_frontend.entities.user.User;
import com.minortechnologies.workr_frontend.usecase.security.Security;
import com.minortechnologies.workr_frontend.usecase.factories.userfactory.CreateUser;
import org.junit.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.*;

public class SecurityTest {


    String hashString1;
    String hashString2;
    byte[] salt1;
    byte[] salt2;

    @Before
    public void setUp(){
        hashString1 = "TestString";
        hashString2 = "TestString";
        salt1 = Security.generateSalt();
        salt2 = Security.generateSalt();
    }
    
    /**
     * tests that equal inputs generates the same hash (when salt is not used)
     *
     */
    @Test
    public void repeatableHashResults() {
        byte[] hash1 = Security.generateHash(hashString1);
        byte[] hash2 = Security.generateHash(hashString2);
        assertArrayEquals(hash1, hash2);
    }

    /**
     * Tests that hashing the same value with different salts results in different hashes
     */
    @Test
    public void hashWithSaltDifferent(){
        byte[] hash1 = Security.generateHash(hashString1, salt1);
        byte[] hash2 = Security.generateHash(hashString1, salt2);
        assert hash1 != null;
        assert hash2 != null;
        assertEquals(hash1.length, hash2.length);
        int equalBytes = 0;
        for (int i = 0; i < hash1.length; i++) {
            if (hash1[i] == hash2[i]){
                equalBytes++;
            }
        }
        assertNotEquals(equalBytes, hash1.length);
    }

    /**
     * Tests that toHex and fromHex produces reversible results (byte arrays can be converted back and forth)
     */
    @Test
    public void hexReversible(){
        String hex = Security.toHex(salt1);
        byte[] fHex = Security.fromHex(hex);

        assertArrayEquals(fHex, salt1);
    }
}
