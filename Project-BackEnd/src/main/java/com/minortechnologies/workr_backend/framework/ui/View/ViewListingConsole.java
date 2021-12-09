package com.minortechnologies.workr_backend.framework.ui.View;

import com.minortechnologies.workr_backend.framework.ui.View.Interfaces.IViewListing;
import com.minortechnologies.workr_backend.usecase.displaydata.IDisplayData;
import com.minortechnologies.workr_backend.usecase.displaydata.ListingDisplayData;

public class ViewListingConsole implements IViewListing {
    @Override
    public void displayListing(IDisplayData displayData) {
        if (displayData instanceof ListingDisplayData){

        }
    }
}
