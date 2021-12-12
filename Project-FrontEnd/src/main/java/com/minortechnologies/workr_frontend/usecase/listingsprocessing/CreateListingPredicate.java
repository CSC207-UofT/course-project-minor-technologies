package com.minortechnologies.workr_frontend.usecase.listingsprocessing;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.listing.JobType;

import java.time.LocalDate;
import java.util.function.Predicate;

public class CreateListingPredicate {
    /**
     * contains methods to create Predicates to be used as filters in filtering
     */

    //Creates a Predicate that checks if a Listing's JobType matches a given JobType
    public static Predicate<JobListing> jobTypeIs(JobType jobType) {
        return p -> p.getJobType() == jobType;
    }
    //Creates a Predicate that checks if a Listing is listed before a given date
    public static Predicate<JobListing> listingDateBefore(LocalDate date) {
        return p -> p.getListingDate().isBefore(date);
    }
    //Creates a Predicate that checks if a Listing is listed after a given date
    public static Predicate<JobListing> listingDateAfter(LocalDate date) {
        return p -> p.getListingDate().isAfter(date);
    }
    //Creates a Predicate that checks if a Listing's location matches a given location
    public static Predicate<JobListing> locationIs(String location) {
        return p -> p.getData(JobListing.LOCATION).toString().equalsIgnoreCase(location);
    }
    //Creates a Predicate that checks if a Listing's pay is greater than a given amount
    public static Predicate<JobListing> payGreaterThan(Integer pay) {
        return p -> (int) p.getData(JobListing.PAY) > pay;
    }
    //Creates a Predicate that checks if a Listing's pay is less than a given amount
    public static Predicate<JobListing> payLessThan(Integer pay) {
        return p -> (int) p.getData(JobListing.PAY) < pay;
    }
}
