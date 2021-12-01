package com.minortechnologies.workr_frontend.framework.ui.View;

import com.minortechnologies.workr_frontend.framework.ui.View.Interfaces.IViewListing;
import com.minortechnologies.workr_frontend.usecase.displaydata.IDisplayData;
import com.minortechnologies.workr_frontend.usecase.displaydata.ListingDisplayData;

public class ViewListingConsole implements IViewListing {
    @Override
    public void displayListing(IDisplayData displayData) {
        if (displayData instanceof ListingDisplayData){

        }
    }
}
