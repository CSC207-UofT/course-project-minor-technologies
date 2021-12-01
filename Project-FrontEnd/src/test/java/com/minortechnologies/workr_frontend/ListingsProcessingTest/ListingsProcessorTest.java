package com.minortechnologies.workr_frontend.ListingsProcessingTest;

//OUTDATED CAN'T TEST ANYMORE
/**
public class ListingsProcessorTest {
    JobType j1 = JobType.FULL_TIME;
    JobType j2 = JobType.PART_TIME;
    CreateCustomListing listingsCreator = new CreateCustomListing();
    CustomJobListing l1 = listingsCreator.create("Software engineer", "Toronto", 100000, j1,
            "College Degree", "1 year experience", "Resume and Cover letter",
            "Engineering in Python and others", "LinkedIn");
    CustomJobListing l2 = new CustomJobListing("Chemical engineer", "Toronto", 75000, j1,
            "College Degree", "1 year experience", "Resume and Cover letter",
            "Engineering in Material Chemistry", "LinkedIn");
    CustomJobListing l3 = new CustomJobListing("Walmart Cashier", "Toronto", 10000, j2,
            "High School Diploma", "None", "Resume",
            "Operating cashier at checkout", "LinkedIn");
    CustomJobListing l4 = new CustomJobListing("Cheese Factory Line Cook", "Toronto", 12500, j2,
            "High School Diploma", "None", "Resume",
            "Making them cheesecakes", "LinkedIn");
    CustomJobListing l5 = new CustomJobListing("UNIQLO Sales Associate", "Toronto", 12500, j2,
            "High School Diploma", "None", "Resume",
            "Do your job", "LinkedIn");
    CustomJobListing l6 = new CustomJobListing("Montréal Pastries", "Montréal", 50000, j1,
            "High School Diploma", "Baking experience", "Resume and Cover letter",
            "MAKE THE CAKE", "LinkedIn");
    CustomJobListing l7 = new CustomJobListing("Montreal Pastries", "Montréal", 50000, j1,
            "High School Diploma", "Baking experience", "Resume and Cover letter",
            "MAKE THE CAKE", "LinkedIn");
    CustomJobListing l8 = new CustomJobListing("montreal Pastries", "Montréal", 50000, j1,
            "High School Diploma", "Baking experience", "Resume and Cover letter",
            "MAKE THE CAKE", "LinkedIn");
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
        ArrayList<Listing> processed = processor.processList(listings);
        ArrayList<Listing> expected = new ArrayList<>();
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
    public void testNoFilter() {
        ListingsProcessor processor = new DefaultProcessor();
        ArrayList<Listing> processed = processor.processList(listings, new SalaryComparator());
        ArrayList<Listing> expected = new ArrayList<>();
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
        Predicate<Listing> isFullTime = Listing -> Listing.getJobType() == JobType.FULL_TIME;
        ArrayList<Predicate<Listing>> filters = new ArrayList<>();
        filters.add(isFullTime);

        ListingsProcessor processor = new DefaultProcessor();
        ArrayList<Listing> processed = processor.processList(listings, filters);
        ArrayList<Listing> expected = new ArrayList<>();
        expected.add(l2);
        expected.add(l8);
        expected.add(l7);
        expected.add(l6);
        expected.add(l1);

        Assert.assertEquals(expected, processed);
    }

    @Test
    public void testFilterBySalary() {
        Predicate<Listing> moreThan30k = Listing -> 30000 < Listing.getPay();
        Predicate<Listing> lessThan80k = Listing -> Listing.getPay() < 80000;
        ArrayList<Predicate<Listing>> filters = new ArrayList<>();
        filters.add(moreThan30k);
        filters.add(lessThan80k);

        ListingsProcessor processor = new DefaultProcessor();
        ArrayList<Listing> processed = processor.processList(listings, filters, new SalaryComparator());
        ArrayList<Listing> expected = new ArrayList<>();
        expected.add(l2);
        expected.add(l8);
        expected.add(l7);
        expected.add(l6);

        Assert.assertEquals(expected, processed);
    }

    @Test
    public void testFilterByMultipleReturnEmpty() {
        Predicate<Listing> isPartTime = Listing -> Listing.getJobType() == JobType.PART_TIME;
        Predicate<Listing> moreThan30k = Listing -> 30000 < Listing.getPay();
        Predicate<Listing> lessThan80k = Listing -> Listing.getPay() < 80000;
        ArrayList<Predicate<Listing>> filters = new ArrayList<>();
        filters.add(isPartTime);
        filters.add(moreThan30k);
        filters.add(lessThan80k);

        ListingsProcessor processor = new DefaultProcessor();
        ArrayList<Listing> processed = processor.processList(listings, filters, new SalaryComparator());
        ArrayList<Listing> expected = new ArrayList<>();

        Assert.assertEquals(expected, processed);
    }

    @Test
    public void testMultipleFiltersQuickSort() {
        Predicate<Listing> isPartTime = Listing -> Listing.getJobType() == JobType.FULL_TIME;
        Predicate<Listing> moreThan10k = Listing -> 10000 < Listing.getPay();
        Predicate<Listing> lessThan80k = Listing -> Listing.getPay() < 80000;
        Predicate<Listing> inMontreal = Listing -> Listing.getLocation() == "Montréal";
        ArrayList<Predicate<Listing>> filters = new ArrayList<>();
        filters.add(isPartTime);
        filters.add(moreThan10k);
        filters.add(lessThan80k);
        filters.add(inMontreal);

        ListingsProcessor processor = new QuickProcessor();
        ArrayList<Listing> processed = processor.processList(listings, filters, new SalaryComparator());
        ArrayList<Listing> expected = new ArrayList<>();
        expected.add(l6);
        expected.add(l7);
        expected.add(l8);

        Assert.assertEquals(expected, processed);
    }
}
 */
