package com.minortechnologies.workr_backend.entities.employer;

import com.minortechnologies.workr_backend.entities.listing.JobListing;

public interface IEmployer {

    boolean addEmployerJobListing(JobListing jobListing);
}
