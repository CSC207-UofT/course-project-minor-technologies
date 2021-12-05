package com.minortechnologies.workr_frontend.usecase.listingsprocessing;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DefaultProcessor extends ListingsProcessor {
    /**
     * A child of com.minortechnologies.workr.UseCase.ListingsProcessing.ListingsProcessor which implements abstract method sort()
     * using Java's Collection.sort()'s default sorting algorithm, and sorts
     * by descending order.
     *
     * Note. I believe Java uses MergeSort by default (could be wrong)
     *
     * @param listings the List of listings to be sorted
     * @param comparator a comparator object in which contains the
     *                   criteria for comparing listings
     * @return the sorted List of listings
     */
    protected List<JobListing> sort(List<JobListing> listings, Comparator<JobListing> comparator) {
        ArrayList<JobListing> toSort = new ArrayList<JobListing>(listings);
        toSort.sort(comparator);
        // We reverse here because we choose to sort descending by default
        Collections.reverse(toSort);
        List<JobListing> sorted = toSort;
        return sorted;
    }
}