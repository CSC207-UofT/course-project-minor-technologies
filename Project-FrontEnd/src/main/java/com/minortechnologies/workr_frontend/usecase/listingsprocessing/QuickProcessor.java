package com.minortechnologies.workr_frontend.usecase.listingsprocessing;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuickProcessor extends ListingsProcessor {
    /**
     * A child of com.minortechnologies.workr.UseCase.ListingsProcessing.ListingsProcessor which implements abstract method sort()
     * using the QuickSort sorting algorithm, and sorts by descending order.
     *
     * @param listings   the ArrayList of listings to be sorted
     * @param toCompare the criteria used to compare JobListings
     *
     * @return the sorted ArrayList of listings
     */
    protected List<JobListing> sort(List<JobListing> listings, String toCompare) {
        // Don't need to sort
        if (listings.size() <= 1)
            return listings;

        ArrayList<JobListing> lesser = new ArrayList<>();
        ArrayList<JobListing> greater = new ArrayList<>();

        Comparator<JobListing> comparator = new JobListingComparator(toCompare);

        // Pivot set to last listing
        JobListing pivot = listings.get(listings.size() - 1);
        for (int i = 0; i < listings.size() - 1; i++) {
            // >= because we sort by descending
            if (comparator.compare(listings.get(i), pivot) >= 0)
                lesser.add(listings.get(i));
            else
                greater.add(listings.get(i));
        }

        lesser = (ArrayList<JobListing>) sort(lesser, toCompare);
        greater = (ArrayList<JobListing>) sort(greater, toCompare);

        lesser.add(pivot);
        lesser.addAll(greater);
        return lesser;
    }
}