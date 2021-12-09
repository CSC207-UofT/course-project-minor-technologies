package com.minortechnologies.workr_frontend.usecase;

import com.minortechnologies.workr_frontend.entities.Entry;

import java.util.ArrayList;
import java.util.Iterator;

public interface IDatabase extends Iterable<Entry> {

    boolean addEntry(Entry entry);

    /**
     * Adds entries into the database
     *
     * @param entries an iterable collection of entries
     * @return a list containing entries that were not successfully added.
     */
    ArrayList<Entry> addEntries(Iterable<Entry> entries);

    /**
     * Removes the provided entry from the database
     *
     * @param entry the entry to be removed from the database
     * @return whether the entry had been removed successfully
     */
    boolean removeEntry(Entry entry);

    /**
     * Updates an entry in the database.
     *
     * The getEquivalent() method is used to determine which entry to update.
     *
     * @param entry The entry to be updated with the current data.
     * @return whether the entry had been successfully updated
     */
    boolean updateEntry(Entry entry);

    /**
     * Whether this database contains this entry
     *
     * @param entry the entry to search for
     * @return whether the database contains the entry.
     */
    boolean contains(Entry entry);

    /**
     * The current size of this database
     *
     * @return the size of this database.
     */
    int size();

    Entry getEquivalent(Entry entry);

    Iterator<Entry> iterator();
}
