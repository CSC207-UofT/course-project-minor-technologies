package com.minortechnologies.workr_frontend.usecase.listingsprocessing;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;

import java.text.Collator;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Locale;

public class JobListingComparator implements Comparator<JobListing> {

    private final String toCompare;

    /**
     * Constructs an object of JobListingComparator
     *
     * @param toCompare the criteria used to compare JobListings
     */
    public JobListingComparator(String toCompare){
        this.toCompare = toCompare;
    }

    /**
     * Compares its two arguments for order.
     * Returns a negative integer, zero, or a positive integer
     * as l1 is less than, equal to, or greater than l2
     *
     * @param l1 the first Listing to compare
     * @param l2 the second Listing to compare
     * @return a negative integer, zero, or a positive integer
     *         as l1 is less than, equal to, or greater than l2
     */

    @Override
    public int compare(JobListing l1, JobListing l2){
        switch (toCompare) {
            case "TITLE":
                return compareByTitle(l1, l2);
            case "LISTING_DATE":
                return compareByListingDate(l1, l2);
            case "PAY":
                return compareBySalary(l1, l2);
            default:
                return 0;
        }
    }

    //Example: abc > Abc > ABC > Àbc > àbc > Äbc > äbc
    public int compareByTitle (JobListing l1, JobListing l2) {
        String s1 = l1.getTitle();
        String s2 = l2.getTitle();

        // Get the Collator for Canadian English and set its strength to TERTIARY
        Collator caCollator = Collator.getInstance(Locale.CANADA);
        caCollator.setStrength(Collator.TERTIARY);
        // Normally a < A < b < B, but we choose to let a > A > b > B, so we reverse the sign of the result
        return caCollator.compare(s1, s2) * -1;
    }

    //l1 is greater than l2 if it is posted after l2 (if l1 was posted more recently).
    public int compareByListingDate (JobListing l1, JobListing l2) {
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

    //l1 is greater than l2 if its salary is greater than that of l2's
    public int compareBySalary (JobListing l1, JobListing l2) {
        int p1 = Integer.valueOf(l1.getData(JobListing.PAY).toString());
        int p2 = Integer.valueOf(l2.getData(JobListing.PAY).toString());

        return p1 - p2;
    }
}
