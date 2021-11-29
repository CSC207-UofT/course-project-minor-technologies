package com.minortechnologies.workr_frontend.controllers.backgroundoperations;

import java.util.HashSet;
import java.util.Map;

import com.minortechnologies.workr_frontend.controllers.localcache.*;
import com.minortechnologies.workr_frontend.controllers.usermanagement.UserManagement;
import com.minortechnologies.workr_frontend.entities.Entry;
import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.main.Main;
import com.minortechnologies.workr_frontend.usecase.factories.ICreateEntry;
import com.minortechnologies.workr_frontend.usecase.fileio.MalformedDataException;

public class BackgroundUpdateListings implements IBackgroundOperation{
    @Override


    public void run() {
        while (BackgroundOperations.isRunBackgroundOps()){
            try {
                updateListings();
                Thread.sleep(BackgroundOperations.getUpdateInterval());
            } catch (InterruptedException e) {
                updateListings();
            }
        }
    }

    /**
     * Gets all locally saved listings, updates them (make an HTTP call with the UUIDs of each of them).
     */
    private void updateListings(){

    }
}
