package com.minortechnologies.workr_backend.usecase.factories.joblisting;

import com.minortechnologies.workr_backend.entities.listing.CustomJobListing;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class CreateCustomJobListing implements ICreateJobListing {

    public CreateCustomJobListing(){
    }

    /**
     * Creates a CustomListing from a JSONData.
     *
     *
     * @param listingJsonData - a JSONObject containing the data for a CustomListing
     * @return A CustomListing from the listingJsonData
     * @throws IOException if the JSONData is missing keys required for this listing type
     */
    @Override
    public JobListing create(Map<String, Object> listingJsonData) throws MalformedDataException {
        ArrayList<String> missingKeys = verifyMapIntegrity(listingJsonData);
        if (missingKeys.size() == 0){
            return new CustomJobListing(listingJsonData);
        }
        else{
            throw new MalformedDataException(ICreateEntry.missingKeyInfo(missingKeys, "CUSTOM JOB LISTING"));
        }
    }

    @Override
    public @NotNull ArrayList<String> verifyMapIntegrity(Map<String, Object> entryDataMap){
        ArrayList<String> missingKeys = new ArrayList<>();
        Set<String> keys = entryDataMap.keySet();

        if (!keys.contains("origin")){
            missingKeys.add("origin");
        }
        return missingKeys;
    }
}
