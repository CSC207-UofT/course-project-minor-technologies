package com.minortechnologies.workr_backend.usecase.listing;

import com.minortechnologies.workr_backend.entities.Entry;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.listing.ListingType;
import com.minortechnologies.workr_backend.usecase.IDatabase;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class JobListingDB implements IDatabase{

    private final HashMap<ListingType, ArrayList<JobListing>> listingDB;

    public JobListingDB(ArrayList<Entry> jobListings){
        listingDB = new HashMap<>();

        for (Entry entry :
                jobListings) {
            if (entry instanceof JobListing){
                JobListing jobListing = (JobListing) entry;
                addEntry(jobListing);
            }
        }
    }

    public JobListingDB(){
        listingDB = new HashMap<>();
    }

    /**
     * Adds a listing to the database if the listing does not already exist in the database
     *
     * @param entry - The entry to be added to the database
     * @return true of the listing was successfully added to the database. False otherwise
     */
    public boolean addEntry(Entry entry) {
        if (entry instanceof JobListing){
            JobListing jobListing = (JobListing) entry;
            ListingType type = jobListing.getListingType();

            if (!listingDB.containsKey(type)){
                listingDB.put(type, new ArrayList<>());
            }

            if (getIndex(jobListing) == -1) {
                listingDB.get(type).add(jobListing);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Entry> addEntries(Iterable<Entry> entries) {
        ArrayList<Entry> failedAdds = new ArrayList<>();
        for (Entry entry:
                entries) {
            if (!addEntry(entry)){
                failedAdds.add(entry);
            }
        }
        return failedAdds;
    }

    /**
     * Update the listing by removing the old listing and replacing it with a new one. If no old listing exists, it
     * simply adds the listing to the database;
     * returns true if listing was successfully updated.
     * returns false if listing was not updated for any reason.
     *
     * // This is done this way for thread safety. If listing was being updated as, for example, a driver was attempting
     * // to display it, it would cause some concurrentAccess related exceptions. Until I figure out how that can be
     * // correctly done, the listing would have to be replaced entirely.
     *
     * @param entry the entry to be added
     * @return whether the entry had been updated
     */
    @Override
    public boolean updateEntry(Entry entry){

        Entry equivEntry = getEquivalent(entry);
        if (equivEntry != null){
            equivEntry.updateEntry(entry);
            return true;
        }
        return false;
    }

    /**
     * returns true if an Entry is contained in this database, otherwise returns false.
     *
     * @param entry the entry to search for
     * @return whether the entry is contained in this database.
     */
    @Override
    public boolean contains(Entry entry) {
        if (entry instanceof JobListing){
            return (getIndex((JobListing) entry) != -1);
        }
        return false;
    }

    /**
     * returns true if there is an Equivalent entry in this database, determined by the Entry.isEquivalent method.
     * TODO: complete docstring
     * @param entry The entry to find an equivalent entry for
     * @return The entry that was found to be equivalent to the provided entry.
     */
    @Override
    public Entry getEquivalent(Entry entry){
        if (entry instanceof JobListing){

            JobListing castedEntry = (JobListing)entry;

            ListingType type = (ListingType) castedEntry.getData(JobListing.LISTING_TYPE);

            if (listingDB.containsKey(type)){
                ArrayList<JobListing> listingsOfType = listingDB.get(type);

                for (JobListing listing:
                        listingsOfType) {
                    if (listing.isEquivalent(castedEntry)){
                        return listing;
                    }
                }
            }
        }
        return null;
    }

    // TODO: complete docstring
    /**
     * returns the index for a listing with a matching UID;
     * returns -1 if there are no listings with the matching UID;
     *
     * @param jobListing A JobListing object to find the index for
     * @return the index of the provided listing.
     */
    private int getIndex(JobListing jobListing){
        ListingType type = jobListing.getListingType();
        ArrayList<JobListing> db = listingDB.get(type);
        for (int i = 0; i < db.size(); i++) {
            if (Objects.equals(db.get(i).getUUID(), jobListing.getUUID())){
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes the provided entry from the database.
     *
     * @param entry the entry to remove from the database
     * @return whether the entry was successfully removed from the database.
     */
    @Override
    public boolean removeEntry(Entry entry) {
        if (entry instanceof JobListing){
            JobListing jobListing = (JobListing) entry;
            int index = getIndex(jobListing);
            if (index != -1){
                listingDB.get(jobListing.getListingType()).remove(index);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {

        int size = 0;
        for (ListingType key:
                listingDB.keySet()) {
            size += listingDB.get(key).size();
        }
        return size;
    }


    @NotNull
    @Override
    public Iterator<Entry> iterator() {
        return new JobListingDBIterator(this.listingDB);
    }


    static class JobListingDBIterator implements Iterator<Entry>{

        private final Iterator<JobListing> toIterate;

        public JobListingDBIterator(HashMap<ListingType, ArrayList<JobListing>> listingMap){
            ArrayList<JobListing> totalList = new ArrayList<>();
            for (ListingType key:
                 listingMap.keySet()) {
                totalList.addAll(listingMap.get(key));
            }
            toIterate = totalList.iterator();
        }

        @Override
        public boolean hasNext() {
            return toIterate.hasNext();
        }

        @Override
        public Entry next() {
            return toIterate.next();
        }
    }
}
