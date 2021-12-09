package com.minortechnologies.workr_frontend.usecase.listingsprocessing;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public abstract class ListingsProcessor {
    /**
     * abstract class com.minortechnologies.workr.UseCase.ListingsProcessing.ListingsProcessor
     * containing template method processList and implementation
     * of steps which is the same for all types of processors.
     * Those implementations have been marked as final.
     * @param listings the List of listings to be filtered and sorted
     * @param filters a List of Predicates providing conditions for
     *                whether a given listing should be filtered out
     * @param toCompare the criteria used to compare JobListings
     * @return the filtered and sorted List of listings
     */
    public final List<JobListing> processList(List<JobListing> listings, List<Predicate<JobListing>> filters,
                                              String toCompare) {
        List<JobListing> filteredListings = filter(listings, filters);
        return sort(filteredListings, toCompare);
    }
    /**
     * default method when no comparator is provided (sorts alphabetically ascending, A to Z by default)
     */
    public final List<JobListing> processList(List<JobListing> listings, List<Predicate<JobListing>> filters) {
        List<JobListing> filteredListings = filter(listings, filters);
        return sort(filteredListings, "TITLE");
    }
    /**
     * default method when no filters are provided
     */
    public final List<JobListing> processList(List<JobListing> listings, String toCompare) {
        List<Predicate<JobListing>> emptyList = Collections.emptyList();
        List<JobListing> filteredListings = filter(listings, emptyList);
        return sort(filteredListings, toCompare);
    }
    /**
     * default method when no comparator or filters are provided
     */
    public final List<JobListing> processList(List<JobListing> listings) {
        List<Predicate<JobListing>> emptyList = Collections.emptyList();
        List<JobListing> filteredListings = filter(listings, emptyList);
        return sort(filteredListings, "TITLE");
    }
    /**
     * Creates a new List containing only listings that meet the criteria given by filters
     *
     * @param listings the List of listings to be filtered and sorted
     * @param filters a List of Predicates providing conditions for
     *                whether a given listing should be filtered out
     * @return the filtered List of listings
     */
    private static List<JobListing> filter(List<JobListing> listings, List<Predicate<JobListing>> filters) {
        // when no filters are given
        if(filters.size() == 0){
            return listings;
        }
        // combines all Predicates into a single composite Predicate
        Predicate<JobListing> filter = filters.get(0);
        for(int i = 0; i < filters.size(); i++){
            filter = filter.and(filters.get(i));
        }
        ArrayList<JobListing> toFilter = new ArrayList<>(listings);
        // filters listings by the composite predicate and returns a List containing those which pass
        toFilter = toFilter.stream().filter(filter).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return toFilter;
    }

    abstract List<JobListing> sort(List<JobListing> listings, String toCompare);
}