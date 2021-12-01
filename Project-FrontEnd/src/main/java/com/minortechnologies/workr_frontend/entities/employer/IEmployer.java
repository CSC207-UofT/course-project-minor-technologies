package com.minortechnologies.workr_frontend.entities.employer;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;

public interface IEmployer {

    boolean addEmployerJobListing(JobListing jobListing);
}
