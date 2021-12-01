package com.minortechnologies.workr_frontend.SerializationTests.FactoryTests;

import com.minortechnologies.workr_frontend.entities.Entry;
import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.user.User;
import com.minortechnologies.workr_frontend.framework.fileio.FileIO;
import com.minortechnologies.workr_frontend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_frontend.usecase.fileio.IEntryDeserializer;
import com.minortechnologies.workr_frontend.usecase.fileio.JSONSerializer;
import com.minortechnologies.workr_frontend.usecase.fileio.MalformedDataException;

import static org.junit.Assert.*;
import org.junit.*;

import java.io.File;
import java.util.HashMap;


public class ICreateEntryTests {


    /**
     * These tests assume that the FileIO and DataFormat has been correctly implemented.
     *
     */

    HashMap<String, Object> jobListingData;
    HashMap<String, Object> userData;
    String sep = File.separator;

    @Before
    public void setUp(){
        String testFiles =  "test" + sep + "SerializationTests" + sep + "FactoryTests" + sep + "FactoryTestFiles";
        IEntryDeserializer deserializer = new JSONSerializer();

        String jlPath = testFiles + sep + "JL1.json";
        String userPath = testFiles + sep + "User1.json";

        String jlString = FileIO.readFile(jlPath);
        String userString = FileIO.readFile(userPath);
        jobListingData = deserializer.deserialize(jlString);
        userData = deserializer.deserialize(userString);
    }

    @Test
    public void correctSubclassCreation(){
        try{
            Entry jlEntry = ICreateEntry.createEntry(jobListingData);
            Entry userEntry = ICreateEntry.createEntry(userData);

            assertTrue(jlEntry instanceof JobListing);
            assertTrue(userEntry instanceof User);
        } catch (MalformedDataException e) {
            fail();
        }
    }
}
