package com.minortechnologies.workr_backend.usecase.listingsprocessing;

import com.minortechnologies.workr_backend.entities.listing.JobListing;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

public abstract class ListingsProcessor {
    /**
     * abstract class com.minortechnologies.workr.UseCase.ListingsProcessing.ListingsProcessor containing template method processList and implementation
     * of steps which is the same for all types of processors. Those implementations have
     * been marked as final.
     * @param listings the ArrayList of listings to be filtered and sorted
     * @param filters an ArrayList of Predicates providing conditions for
     *                whether a given listing should be filtered out
     * @param comparator a comparator object in which contains the criteria
     *                   for comparing listings
     * @return the filtered and sorted ArrayList of listings
     */
    public final ArrayList<JobListing> processList(ArrayList<JobListing> listings, ArrayList<Predicate<JobListing>> filters,
                                                   Comparator<JobListing> comparator) {
        ArrayList<JobListing> filteredListings = filter(listings, filters);
        ArrayList<JobListing> sortedListings = sort(filteredListings, comparator);
        return sortedListings;
    }
    /**
     * default method when no comparator is provided (sorts alphabetically ascending, A to Z by default)
     */
    public final ArrayList<JobListing> processList(ArrayList<JobListing> listings, ArrayList<Predicate<JobListing>> filters) {
        ArrayList<JobListing> filteredListings = filter(listings, filters);
        ArrayList<JobListing> sortedListings = sort(filteredListings, new AlphabeticalComparator());
        return sortedListings;
    }
    /**
     * default method when no filters are provided
     */
    public final ArrayList<JobListing> processList(ArrayList<JobListing> listings, Comparator<JobListing> comparator) {
        ArrayList<JobListing> filteredListings = filter(listings, new ArrayList<>());
        ArrayList<JobListing> sortedListings = sort(filteredListings, comparator);
        return sortedListings;
    }
    /**
     * default method when no comparator or filters are provided
     */
    public final ArrayList<JobListing> processList(ArrayList<JobListing> listings) {
        ArrayList<JobListing> filteredListings = filter(listings, new ArrayList<>());
        ArrayList<JobListing> sortedListings = sort(filteredListings, new AlphabeticalComparator());
        return sortedListings;
    }
    /**
     * Creates a new ArrayList containing only listings that meet the criteria given by filters
     *
     * @param listings the ArrayList of listings to be filtered and sorted
     * @param filters an ArrayList of Predicates providing conditions for
     *                whether a given listing should be filtered out
     * @return the filtered ArrayList of listings
     */
    private static ArrayList<JobListing> filter(ArrayList<JobListing> listings, ArrayList<Predicate<JobListing>> filters) {
        // when no filters are given
        if(filters.size() == 0){
            return listings;
        }
        // combines all Predicates into a single composite Predicate
        Predicate<JobListing> filter = filters.get(0);
        for(int i = 0; i < filters.size(); i++){
            filter = filter.and(filters.get(i));
        }
        ArrayList<JobListing> filtered = (ArrayList<JobListing>) listings.clone();
        // filters listings by the composite predicate and returns an ArrayList containing those which pass
        filtered = filtered.stream().filter(filter).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return filtered;
    }

    abstract ArrayList<JobListing> sort(ArrayList<JobListing> listings, Comparator<JobListing> comparator);
}