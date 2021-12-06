package com.minortechnologies.workr_frontend.ListingsProcessingTest;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.listing.JobType;
import com.minortechnologies.workr_frontend.usecase.listingsprocessing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ListingsProcessorTest {

    JobListing l1 = TestData.createJobListing1();
    JobListing l2 = TestData.createJobListing2();
    JobListing l3 = TestData.createJobListing3();
    JobListing l4 = TestData.createJobListing4();
    JobListing l5 = TestData.createJobListing5();
    JobListing l6 = TestData.createJobListing6();
    JobListing l7 = TestData.createJobListing7();
    JobListing l8 = TestData.createJobListing8();
    ArrayList<JobListing> listings = new ArrayList<>();

    @Before
    public void setUp() {
        listings.add(l1);
        listings.add(l2);
        listings.add(l3);
        listings.add(l4);
        listings.add(l5);
        listings.add(l6);
        listings.add(l7);
        listings.add(l8);
    }

    @Test
    public void testNoFilterNoComparator() {
        ListingsProcessor processor = new DefaultProcessor();
        List<JobListing> processed = processor.processList(listings);
        List<JobListing> expected = new ArrayList<>();
        expected.add(l4);
        expected.add(l2);
        expected.add(l8);
        expected.add(l7);
        expected.add(l6);
        expected.add(l1);
        expected.add(l5);
        expected.add(l3);

        Assert.assertEquals(expected, processed);
    }

    @Test
    public void testNoFilterSortBySalary() {
        ListingsProcessor processor = new DefaultProcessor();
        List<JobListing> processed = processor.processList(listings, "PAY");
        List<JobListing> expected = new ArrayList<>();
        expected.add(l1);
        expected.add(l2);
        expected.add(l8);
        expected.add(l7);
        expected.add(l6);
        expected.add(l5);
        expected.add(l4);
        expected.add(l3);

        Assert.assertEquals(expected, processed);
    }

    @Test
    public void testFilterByJobType() {
        Predicate<JobListing> isFullTime = CreateListingPredicate.jobTypeIs(JobType.FULL_TIME);
        ArrayList<Predicate<JobListing>> filters = new ArrayList<>();
        filters.add(isFullTime);

        ListingsProcessor processor = new DefaultProcessor();
        List<JobListing> processed = processor.processList(listings, filters);
        List<JobListing> expected = new ArrayList<>();
        expected.add(l2);
        expected.add(l8);
        expected.add(l7);
        expected.add(l6);
        expected.add(l1);

        Assert.assertEquals(expected, processed);
    }

    @Test
    public void testFilterSortBySalary() {
        Predicate<JobListing> moreThan30k = CreateListingPredicate.payGreaterThan(30000);
        Predicate<JobListing> lessThan80k = CreateListingPredicate.payLessThan(80000);
        ArrayList<Predicate<JobListing>> filters = new ArrayList<>();
        filters.add(moreThan30k);
        filters.add(lessThan80k);

        ListingsProcessor processor = new DefaultProcessor();
        List<JobListing> processed = processor.processList(listings, filters, "PAY");
        List<JobListing> expected = new ArrayList<>();
        expected.add(l2);
        expected.add(l8);
        expected.add(l7);
        expected.add(l6);

        Assert.assertEquals(expected, processed);
    }

    @Test
    public void testFilterSortByListingDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Predicate<JobListing> beforeDec1 = CreateListingPredicate.listingDateBefore(LocalDate.parse("2021-12-01", formatter));
        Predicate<JobListing> afterFeb25 = CreateListingPredicate.listingDateAfter(LocalDate.parse("2021-02-25", formatter));
        ArrayList<Predicate<JobListing>> filters = new ArrayList<>();
        filters.add(beforeDec1);
        filters.add(afterFeb25);

        ListingsProcessor processor = new DefaultProcessor();
        List<JobListing> processed = processor.processList(listings, filters, "LISTING_DATE");
        List<JobListing> expected = new ArrayList<>();
        expected.add(l4);
        expected.add(l3);
        expected.add(l2);
        expected.add(l1);
        expected.add(l8);
        expected.add(l7);
        expected.add(l6);

        Assert.assertEquals(expected, processed);
    }

    @Test
    public void testFilterByMultipleReturnEmpty() {
        Predicate<JobListing> isPartTime = CreateListingPredicate.jobTypeIs(JobType.PART_TIME);
        Predicate<JobListing> moreThan30k = CreateListingPredicate.payGreaterThan(30000);
        Predicate<JobListing> lessThan80k = CreateListingPredicate.payLessThan(80000);
        ArrayList<Predicate<JobListing>> filters = new ArrayList<>();
        filters.add(isPartTime);
        filters.add(moreThan30k);
        filters.add(lessThan80k);

        ListingsProcessor processor = new DefaultProcessor();
        List<JobListing> processed = processor.processList(listings, filters, "PAY");
        List<JobListing> expected = new ArrayList<>();

        Assert.assertEquals(expected, processed);
    }

    @Test
    public void testMultipleFiltersQuickSort() {
        Predicate<JobListing> isPartTime = Listing -> Listing.getJobType() == JobType.FULL_TIME;
        Predicate<JobListing> moreThan10k = CreateListingPredicate.payGreaterThan(10000);
        Predicate<JobListing> lessThan80k = CreateListingPredicate.payLessThan(80000);
        Predicate<JobListing> inMontreal = CreateListingPredicate.locationIs("Montréal");
        ArrayList<Predicate<JobListing>> filters = new ArrayList<>();
        filters.add(isPartTime);
        filters.add(moreThan10k);
        filters.add(lessThan80k);
        filters.add(inMontreal);

        ListingsProcessor processor = new QuickProcessor();
        List<JobListing> processed = processor.processList(listings, filters, "PAY");
        List<JobListing> expected = new ArrayList<>();
        expected.add(l6);
        expected.add(l7);
        expected.add(l8);

        Assert.assertEquals(expected, processed);
    }
}
