import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class LocalCache {


    private final static String LISTING_SAVE_LOCATION = File.separator + "DemoListings" + File.separator;
    public final static HashMap<ListingType, ArrayList<Listing>> listingsMap = new HashMap<>();
    /**
     * Calls on FileIO and DataFormat to load listings. Adds the created listing according to listingsMap according
     * to the ListingType Enum.
     *
     * Load all listings from the folder DemoListings.
     * 
     * For the skeleton program implementation, only CustomListings should exist.
     * 
     * Do not add duplicate listings to listingsMap.
     *
     */
    public static void loadSavedListings(){
        ArrayList<String> fileNames = FileIO.GetFileNamesInDir(LISTING_SAVE_LOCATION, ".json");
        for(String file : fileNames) {
            String jsonDataString = FileIO.ReadFile(LISTING_SAVE_LOCATION + file);
            try {
                Listing listing = DataFormat.createListing(jsonDataString);
                if(!listingsMap.containsKey(listing.listingType)) {
                    listingsMap.put(listing.listingType, new ArrayList<>());
                }
                if(!listingsMap.get(listing.listingType).contains(listing)) {
                    listingsMap.get(listing.listingType).add(listing);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * Save all listings in LocalCache's listingsMap to relPath,
     *
     * The filename should be the UID of the listing with the extension ".json"
     *
     */
    public static void saveAllListings(){
        Set<ListingType> keys = listingsMap.keySet();
        for(ListingType key : keys){
            for (Listing listing : listingsMap.get(key)){
                String jsonDataString = DataFormat.createJSON(listing);
                FileIO.WriteFile( LISTING_SAVE_LOCATION, listing.getUID() + ".json", jsonDataString);
            }
        }
    }

    /**
     * Loads a listing's JSON data and updates the listing, replacing the original instance in listingsMap with the new
     * one.
     *
     */
    public static void loadListingFromUID(int UID) {
        String newJsonDataString = FileIO.ReadFile(LISTING_SAVE_LOCATION + UID + ".json");
        try {
            Listing newListing = DataFormat.createListing(newJsonDataString);
            int dupeInd = containsUID(newListing.listingType, UID);
            if (dupeInd != -1){
                listingsMap.get(newListing.listingType).remove(dupeInd);
            }
            listingsMap.get(newListing.listingType).add(newListing);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the Index of a listing with the given UID and the listingType.
     *
     * returns -1 if no listings have that type.
     */
    private static int containsUID(ListingType listingType, int UID){
        for (int i = 0; i < listingsMap.get(listingType).size(); i++) {
            if (listingsMap.get(listingType).get(i).getUID() == UID){
                return i;
            }
        }
        return -1;
    }

    /**
     * returns a listing with the UID from listingsMap.
     *
     * returns null if there is no listing with the provided UID
     *
     */
    public static Listing getListingFromUID(int UID){
        for (ListingType key :
             listingsMap.keySet()) {
            for (Listing listing:
                 listingsMap.get(key)) {
                if (listing.getUID() == UID){
                    return listing;
                }
            }
        }
        return null;
    }
}