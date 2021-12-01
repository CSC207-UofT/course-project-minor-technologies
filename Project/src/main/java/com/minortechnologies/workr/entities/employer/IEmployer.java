package com.minortechnologies.workr.entities.employer;

import com.minortechnologies.workr.entities.listing.JobListing;

public interface IEmployer {

    boolean addEmployerJobListing(JobListing jobListing);
}
