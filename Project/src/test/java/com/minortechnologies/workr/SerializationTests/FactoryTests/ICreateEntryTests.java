package com.minortechnologies.workr.SerializationTests.FactoryTests;

import com.minortechnologies.workr.entities.Entry;
import com.minortechnologies.workr.entities.listing.JobListing;
import com.minortechnologies.workr.entities.user.User;
import com.minortechnologies.workr.framework.fileio.FileIO;
import com.minortechnologies.workr.usecase.factories.ICreateEntry;
import com.minortechnologies.workr.usecase.fileio.IEntryDeserializer;
import com.minortechnologies.workr.usecase.fileio.JSONSerializer;
import com.minortechnologies.workr.usecase.fileio.MalformedDataException;

import org.junit.*;
import static org.junit.Assert.*;

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
