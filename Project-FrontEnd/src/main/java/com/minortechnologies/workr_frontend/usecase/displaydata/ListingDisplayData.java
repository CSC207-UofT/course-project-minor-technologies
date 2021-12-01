package com.minortechnologies.workr_frontend.usecase.displaydata;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.listing.ListingType;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ListingDisplayData implements IDisplayData{
    private String title;
    private String location;
    private int pay;
    private String jobType;
    private String qualifications;
    private String requirements;
    private String applicationRequirements;
    private String description;
    private String listingDate;
    private ArrayList<ListingPreviewData> crossPlatformDuplicates;
    private ListingType listingType;

    public ListingDisplayData(JobListing jobListing){
        this.title = jobListing.getTitle();
        this.location = (String) jobListing.getData(JobListing.LOCATION);
        this.pay = (int) jobListing.getData(JobListing.PAY);
        switch (jobListing.getJobType()){
            case FULL_TIME:
                this.jobType = "Full Time";
                break;
            case PART_TIME:
                this.jobType = "Job Type";
                break;
        }
        this.qualifications = (String) jobListing.getData(JobListing.QUALIFICATIONS);
        this.requirements = (String) jobListing.getData(JobListing.REQUIREMENTS);
        this.applicationRequirements = (String) jobListing.getData(JobListing.APPLICATION_REQUIREMENTS);
        this.description = (String) jobListing.getData(JobListing.REQUIREMENTS);
        this.listingDate = dateParse((LocalDateTime) jobListing.getData(JobListing.LISTING_DATE));

        ArrayList<ListingPreviewData> cpd = new ArrayList<>();
        this.crossPlatformDuplicates = cpd;
    }

    private String dateParse(LocalDateTime dateTime){
        return dateTime.getDayOfWeek() +
                ", " +
                dateTime.getMonth() +
                " " + dateTime.getDayOfMonth() +
                ", " + dateTime.getYear();
    }
}
