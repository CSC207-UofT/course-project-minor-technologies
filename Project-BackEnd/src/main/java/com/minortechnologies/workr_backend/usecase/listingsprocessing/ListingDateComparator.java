package com.minortechnologies.workr_backend.usecase.listingsprocessing;

import com.minortechnologies.workr_backend.entities.listing.JobListing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

public class ListingDateComparator implements Comparator<JobListing>{
    /**
     * Compares its two arguments for order.
     *
     * Returns a negative integer, zero, or a positive integer
     * as l1 is less than, equal to, or greater than l2 in terms
     * of their ListingDates. l1 is less than l2 if it is posted
     * before l2.
     *
     * @param l1 the first Listing to compare
     * @param l2 the second Listing to compare
     * @return a negative integer, zero, or a positive integer
     *      as l1 is less than, equal to, or greater than l2
     */
    @Override
    public int compare (JobListing l1, JobListing l2) {
        LocalDate d1 = l1.getListingDate();
        LocalDate d2 = l2.getListingDate();

        if(d1.isAfter(d2)) {
            return 1;
        }

        if(d1.isBefore(d2)) {
            return -1;
        }else {
            return 0;
        }
    }
}