/*
This class (and many of the methods in this class) were heavily influenced or taken from these sources:
https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
https://www.baeldung.com/java-password-hashing
 */

package com.minortechnologies.workr_frontend.usecase.security;

import com.minortechnologies.workr_frontend.entities.user.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * The security class deals with security related functions for database. Primarily hashing passwords and logins.
 *
 */
public class Security {

    /**
     * Generates a hash using the BPKDF2 hashing algorithm with salt
     *
     * @param string - the String to be hashed using the algorithm
     * @param salt - the array of bytes that acts as Salt
     * @return byte array representing the hashing result.
     */
    public static byte[] generateHash(String string, byte[] salt){
        try {
            KeySpec spec = new PBEKeySpec(string.toCharArray(), salt, 256, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates a hash using BPKDF2 with a fixed salt (for simple hashing purposes where salt is unnecessary)
     *
     * @param string - the string to be hashes
     * @return byte array representing the hashing result.
     * @throws InvalidKeySpecException - TODO: find out why this would get thrown
     * @throws NoSuchAlgorithmException - Should never throw this as the algorithm selection is hardcoded into the method
     */
    public static byte[] generateHash(String string) throws InvalidKeySpecException, NoSuchAlgorithmException{

        byte[] salt = new byte[] {0};
        return generateHash(string, salt);
    }

    /**
     * Generates salt for password hashing.
     *
     * @return a randomly generated array of 16 bytes which will work as the salt.
     */
    public static byte[] generateSalt(){
        byte[] salt = new byte[16];
        SecureRandom sRand = new SecureRandom();
        sRand.nextBytes(salt);
        return salt;
    }


    /**
     * Converts a Byte Array to hex.
     *
     * @param byteArray the byte array to be converted
     * @return a string that is the hex of the byte array.
     */
    public static String toHex(byte[] byteArray) {
        BigInteger bi = new BigInteger(1, byteArray);
        String hex = bi.toString(16);
        int paddingLength = (byteArray.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    /**
     * Converts hex to a Byte Array
     *
     * @param hex the hex to be converted
     * @return a Byte array with the hex values.
     */
    public static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
