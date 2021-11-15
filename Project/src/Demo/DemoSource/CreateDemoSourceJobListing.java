package Demo.DemoSource;

import Entities.Listing.JobListing;
import UseCase.Factories.ICreateEntry;
import UseCase.Factories.JobListingFactory.ICreateJobListing;
import UseCase.FileIO.MalformedDataException;

import java.util.ArrayList;
import java.util.Map;

public class CreateDemoSourceJobListing implements ICreateJobListing {

    public CreateDemoSourceJobListing(){

    }


    @Override
    public ArrayList<String> verifyMapIntegrity(Map<String, Object> entryDataMap) {
        ArrayList<String> mk = new ArrayList<>();
        if (!entryDataMap.containsKey(DemoSourceJobListing.DEMO_SOURCE_ID)){
            mk.add(DemoSourceJobListing.DEMO_SOURCE_ID);
        }
        return mk;
    }

    @Override
    public JobListing create(Map<String, Object> listingDataMap) throws MalformedDataException {
        ArrayList<String> mks = verifyMapIntegrity(listingDataMap);
        if (mks.size() == 0){
            return new DemoSourceJobListing(listingDataMap);
        }
        else{
            throw new MalformedDataException(ICreateEntry.missingKeyInfo(mks, "DemoSourceJobListing"));
        }
    }
}
