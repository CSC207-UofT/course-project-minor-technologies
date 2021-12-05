package com.minortechnologies.workr_frontend.usecase.listingsprocessing;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.listing.JobType;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
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
        return p -> p.getListingDate().isBefore(ChronoLocalDateTime.from(date));
    }
    //Creates a Predicate that checks if a Listing is listed after a given date
    public static Predicate<JobListing> listingDateAfter(LocalDate date) {
        return p -> p.getListingDate().isAfter(ChronoLocalDateTime.from(date));
    }
    //Creates a Predicate that checks if a Listing's location matches a given location
    public static Predicate<JobListing> locationIs(String location) {
        return p -> p.LOCATION == location;
    }
    //Creates a Predicate that checks if a Listing's pay is greater than a given amount
    public static Predicate<JobListing> payGreaterThan(Integer pay) {
        return p -> Integer.valueOf(p.PAY) > pay;
    }
    //Creates a Predicate that checks if a Listing's pay is less than a given amount
    public static Predicate<JobListing> payLessThan(Integer pay) {
        return p -> Integer.valueOf(p.PAY) < pay;
    }
}
