package com.minortechnologies.workr.usecase.listingsprocessing;

import com.minortechnologies.workr.entities.listing.JobListing;

import java.util.Comparator;

public class SalaryComparator implements Comparator<JobListing>{
    /**
     * Compares its two arguments for order.
     *
     * Returns a negative integer, zero, or a positive integer
     * as l1 is less than, equal to, or greater than l2 in terms
     * of their salaries.
     *
     * @param l1 the first Listing to compare
     * @param l2 the second Listing to compare
     * @return a negative integer, zero, or a positive integer
     *      as l1 is less than, equal to, or greater than l2
     */
    @Override
    public int compare (JobListing l1, JobListing l2) {
        int p1 = Integer.valueOf(l1.PAY);
        int p2 = Integer.valueOf(l2.PAY);

        return p1 - p2;
    }
}