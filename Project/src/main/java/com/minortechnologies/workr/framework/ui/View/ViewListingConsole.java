package com.minortechnologies.workr.framework.ui.View;

import com.minortechnologies.workr.framework.ui.View.Interfaces.IViewListing;
import com.minortechnologies.workr.usecase.displaydata.IDisplayData;
import com.minortechnologies.workr.usecase.displaydata.ListingDisplayData;

public class ViewListingConsole implements IViewListing {
    @Override
    public void displayListing(IDisplayData displayData) {
        if (displayData instanceof ListingDisplayData){

        }
    }
}
