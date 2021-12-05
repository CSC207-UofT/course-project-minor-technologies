package com.minortechnologies.workr_backend.entities;

import com.minortechnologies.workr_backend.usecase.fileio.MalformedDataException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An entry abstract class. Contains some abstract methods to be implemented.
 *
 * This abstract class's constructor creates a HashMap which uses Strings as keys. This hashmap instance itself cannot
 * be changed, but this object is mutable. This is to ensure child classes does not delete or otherwise accidentally
 * override Entry Data.
 *
 * This abstract class's constructor also checks to ensure that implemented methods use the "Synchronize" methods.
 *
 */
public abstract class Entry {

    public static final String MALFORMED_EXCEPTION_MSG = "Malformed data detected during construction, perhaps Data Map integrity was not checked before construction?";

    private final HashMap<String, Object> entryData;

    private final String[] syncMethods = new String[] {"getEntryData", "serialize", "deserialize", "getData", "addData", "overrideData", "updateEntry"};

    public Entry(){
        entryData = new HashMap<>();
        assertSync();
    }

    /**
     * Checks if a methodName is one of the methods required to use the "Synchronize" keyword.
     *
     * @param methodName a method name to check against an array of method names
     * @return a boolean, true if the methodName is in syncMethods
     */
    private boolean inSyncMethods(String methodName){
        for (String method : syncMethods) {
            if (methodName.equals(method)) {
                return true;
            }
        }
        return false;
    }

    /**
     * asserts that certain methods in subclasses of this class are implemented as synchronous methods to ensure
     * thread safety.
     *
     * this code was taken from this stack overflow post:
     * https://stackoverflow.com/questions/50545604/how-to-ensure-thread-safety-of-subclass-methods-from-a-superclass
     *
     * The only modification to this code is that a second helper function is used to check if the method is one of many
     * as multiple methods in this class needs to use synchronize.
     *
     */
    private void assertSync() {
        Class<? extends Entry> subclass = this.getClass(); // this returns the subclass
        Method[] methods = subclass.getDeclaredMethods();
        for (Method methodName : methods) { // loop through the methods in subclass
            if(inSyncMethods(methodName.getName())) { // when it reaches your method
                String modVal = Modifier.toString(methodName.getModifiers()); // get its modifier
                if(!modVal.contains("synchronized")) { // check if it contains the keyword "synchronized"
                    try { // if not -> throw an Exception with clear message about the reason and exit
                        throw new Exception(methodName +
                                " must be synchronized to ensure class thread safety!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                }
            }
        }
    }

    /**
     * A protected getter for the entry data.
     *
     * This is so Subclasses can access the hashmap if they need to for example, iterate through every entry.
     *
     * @return returns the data HashMap
     */
    protected synchronized HashMap<String, Object> getEntryData(){
        return entryData;
    }

    // TODO: come up with a better name as this might not always be called for serialization purposes.
    /**
     * Prepares a Map representing the Data for serialization.
     *
     * The map returned is a deep copy of the entryData.
     *
     * @return A Map representing the data for this Entry.
     */
    public abstract HashMap<String, Object> serialize();

    /**
     * loads instance variables for the Entry object from deserialized data (in a form of a map).
     *
     * Precondition: Deserialized Data is not malformed (missing keys, etc.).
     *
     * This method also needs to perform a short, minor check to ensure that the deserialized data is properly formatted
     * A simple key-count check is performed. If the check fails, throws a MalformedDataException with the
     * MALFORMED_EXCEPTION_MSG as the exception message, and any optional details of the failed integrity check.
     *
     * @param entryDataMap - the deserialized data stored in a Map data type
     */
    public abstract void deserialize(Map<String, Object> entryDataMap) throws MalformedDataException;

    /**
     * Generates the filename for serialization purposes.
     *
     * @return String the filename (without any file extensions).
     */
    public abstract String getSerializedFileName();

    /**
     * Returns a piece of data from this entry from a given key
     *
     * @param key - The key to the corresponding data to be retrieved
     * @return String - the retrieved data.
     */
    public synchronized Object getData(String key) {
        return entryData.get(key);
    }


    /**
     * Adds data with an associated key for this entry
     * If there's already existing data for that key,
     * returns false, and data is not added/overridden
     *
     * @param key - The key to the corresponding data
     * @param data - The data to be added
     * @return boolean representing operation success.
     */
    public synchronized boolean addData(String key, Object data){
        if (!entryData.containsKey(key) || entryData.get(key) == null){
            entryData.put(key, data);
            return true;
        }
        return false;
    }

    /**
     * Updates data at the corresponding key.
     * If data does not exist at the key, data is not added to the entry.
     *
     * @param key - The key to the corresponding data
     * @param data - The data to be added
     * @return boolean representing operation success.
     */
    public synchronized boolean updateData(String key, Object data){

        if (entryData.containsKey(key)) {
            entryData.put(key, data);
            return true;
        }
        return false;
    }



    /**
     * Updates the data of the entry with the new entryDataMap data.
     *
     * If a key in entryDataMap does not exist in the entry, the data is not added.
     *
     * @param entryDataMap the Map containing new data.
     */
    public abstract void updateEntry(Map<String, Object> entryDataMap);

    /**
     * updates the data of the entry with data from the new Entry.
     *
     * @param entry The new entry data to be added to the entry.
     */
    public abstract void updateEntry(Entry entry);

    /**
     * A method to serialize nestedEntries. Takes an Iterable with Entries and serializes each, adding them to an
     * ArrayList.
     *
     * @param entriesList The iterable of Entries to be serialized.
     * @return An ArrayList with the serialization of each entry.
     */
    protected ArrayList<HashMap<String, Object>> getNestedSerializationData(Iterable<Entry> entriesList){
        ArrayList<HashMap<String, Object>> dataMapList = new ArrayList<>();
        if (entriesList != null){
            for (Entry entry:
                    entriesList ) {
                HashMap<String, Object> preSerializedQueryData = entry.serialize();
                dataMapList.add(preSerializedQueryData);
            }
        }
        return dataMapList;
    }
}
