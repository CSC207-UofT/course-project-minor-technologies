package com.minortechnologies.workr_backend.ListingsProcessingTest;

import com.minortechnologies.workr_backend.entities.listing.CustomJobListing;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.listing.JobType;
import com.minortechnologies.workr_backend.entities.listing.ListingType;

import java.time.LocalDate;

public class TestData {

    /**
     * Creating job listings for unit tests.
     */
    public static CustomJobListing createJobListing1() {
        CustomJobListing jl1 = new CustomJobListing("Software engineer");
        jl1.addData(JobListing.LISTING_TYPE, ListingType.LINKED_IN);
        jl1.addData(JobListing.LOCATION, "Toronto");
        jl1.addData(JobListing.PAY, 100000);
        jl1.addData(JobListing.JOB_TYPE, JobType.FULL_TIME);
        jl1.addData(JobListing.LISTING_DATE, LocalDate.of(2021, 11, 5));
        return jl1;
    }
    public static CustomJobListing createJobListing2() {
        CustomJobListing jl1 = new CustomJobListing("Chemical engineer");
        jl1.addData(JobListing.LISTING_TYPE, ListingType.LINKED_IN);
        jl1.addData(JobListing.LOCATION, "Toronto");
        jl1.addData(JobListing.PAY, 75000);
        jl1.addData(JobListing.JOB_TYPE, JobType.FULL_TIME);
        jl1.addData(JobListing.LISTING_DATE, LocalDate.of(2021, 11, 5));
        return jl1;
    }
    public static CustomJobListing createJobListing3() {
        CustomJobListing jl1 = new CustomJobListing("Walmart Cashier");
        jl1.addData(JobListing.LISTING_TYPE, ListingType.LINKED_IN);
        jl1.addData(JobListing.LOCATION, "Toronto");
        jl1.addData(JobListing.PAY, 10000);
        jl1.addData(JobListing.JOB_TYPE, JobType.PART_TIME);
        jl1.addData(JobListing.LISTING_DATE, LocalDate.of(2021, 11, 6));
        return jl1;
    }
    public static CustomJobListing createJobListing4() {
        CustomJobListing jl1 = new CustomJobListing("Cheese Factory Line Cook");
        jl1.addData(JobListing.LISTING_TYPE, ListingType.LINKED_IN);
        jl1.addData(JobListing.LOCATION, "Toronto");
        jl1.addData(JobListing.PAY, 12500);
        jl1.addData(JobListing.JOB_TYPE, JobType.PART_TIME);
        jl1.addData(JobListing.LISTING_DATE, LocalDate.of(2021, 11, 7));
        return jl1;
    }
    public static CustomJobListing createJobListing5() {
        CustomJobListing jl1 = new CustomJobListing("UNIQLO Sales Associate");
        jl1.addData(JobListing.LISTING_TYPE, ListingType.LINKED_IN);
        jl1.addData(JobListing.LOCATION, "Toronto");
        jl1.addData(JobListing.PAY, 12500);
        jl1.addData(JobListing.JOB_TYPE, JobType.PART_TIME);
        jl1.addData(JobListing.LISTING_DATE, LocalDate.of(2021, 12, 5));
        return jl1;
    }
    public static CustomJobListing createJobListing6() {
        CustomJobListing jl1 = new CustomJobListing("Montréal Pastries");
        jl1.addData(JobListing.LISTING_TYPE, ListingType.LINKED_IN);
        jl1.addData(JobListing.LOCATION, "Montréal");
        jl1.addData(JobListing.PAY, 50000);
        jl1.addData(JobListing.JOB_TYPE, JobType.FULL_TIME);
        jl1.addData(JobListing.LISTING_DATE, LocalDate.of(2021, 9, 5));
        return jl1;
    }
    public static CustomJobListing createJobListing7() {
        CustomJobListing jl1 = new CustomJobListing("Montreal Pastries");
        jl1.addData(JobListing.LISTING_TYPE, ListingType.LINKED_IN);
        jl1.addData(JobListing.LOCATION, "Montréal");
        jl1.addData(JobListing.PAY, 50000);
        jl1.addData(JobListing.JOB_TYPE, JobType.FULL_TIME);
        jl1.addData(JobListing.LISTING_DATE, LocalDate.of(2021, 10, 5));
        return jl1;
    }
    public static CustomJobListing createJobListing8() {
        CustomJobListing jl1 = new CustomJobListing("montreal Pastries");
        jl1.addData(JobListing.LISTING_TYPE, ListingType.LINKED_IN);
        jl1.addData(JobListing.LOCATION, "Montréal");
        jl1.addData(JobListing.PAY, 50000);
        jl1.addData(JobListing.JOB_TYPE, JobType.FULL_TIME);
        jl1.addData(JobListing.LISTING_DATE, LocalDate.of(2021, 10, 5));
        return jl1;
    }
}