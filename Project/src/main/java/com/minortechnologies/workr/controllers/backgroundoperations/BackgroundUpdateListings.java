package com.minortechnologies.workr.controllers.backgroundoperations;

import java.util.HashSet;
import java.util.Map;

import com.minortechnologies.workr.controllers.localcache.*;
import com.minortechnologies.workr.controllers.usermanagement.UserManagement;
import com.minortechnologies.workr.demo.demosource.DemoSourceJobListing;
import com.minortechnologies.workr.demo.TotalDemo;
import com.minortechnologies.workr.entities.Entry;
import com.minortechnologies.workr.entities.listing.JobListing;
import com.minortechnologies.workr.main.Main;
import com.minortechnologies.workr.usecase.factories.ICreateEntry;
import com.minortechnologies.workr.usecase.fileio.MalformedDataException;

public class BackgroundUpdateListings implements IBackgroundOperation{
    @Override
    public void run() {
        while (BackgroundOperations.isRunBackgroundOps()){
            try {
                updateListings();
                // updateListingsTest();
                Thread.sleep(BackgroundOperations.getUpdateInterval());
            } catch (InterruptedException e) {
                updateListings();
                // updateListingsTest();
            }
        }
    }

    private void updateListings(){
        UserManagement um = Main.getUserManagement();
        LocalCache lm = Main.getLocalCache();
        if (Main.getUserManagement().getCurrentActiveUser() != null){
            HashSet<JobListing> watched = um.getCurrentActiveUser().getWatchedListings();
            for (JobListing jobListing :
                    watched) {
                switch (jobListing.getListingType()){
                    case DEMO_SOURCE:
                        updateDemoSourceListing(jobListing);
                        break;
                    default:
                        lm.updateEntryByUUID(jobListing.getUUID());
                        break;
                }

            }
        }
    }

    private void updateDemoSourceListing(JobListing listing){

        if (TotalDemo.getInitiated()){

            String dsid = (String) listing.getData(DemoSourceJobListing.DEMO_SOURCE_ID);
            Map<String, Object> newEntryData = TotalDemo.demoSource.getDemoListingByDSID(dsid);

            try {
                Entry newEntry = ICreateEntry.createEntry(newEntryData);
                listing.updateEntry(newEntry);
            } catch (MalformedDataException e) {
                e.printStackTrace();
            }
        }
    }
}
