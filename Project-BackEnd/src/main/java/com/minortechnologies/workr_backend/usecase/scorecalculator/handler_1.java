package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

import java.util.ArrayList;

public class handler_1 extends handler {


    public handler_1(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates the score given to a user by adding 10 to the score when skills in the
     * user's skill set are present in the qualifications, requirements, application requirements,
     * and description. */
    @Override
    public void score_calculate() {
        double score = 0.0;
        ArrayList<String> data = (ArrayList<String>) this.user.getData(User.SKILL_SET);
        if (data != null) {
            for (String word : data) {
                if (!this.job.getData(JobListing.QUALIFICATIONS).equals("") &&
                        this.job.getData(JobListing.QUALIFICATIONS).toString().toLowerCase().contains(word.toLowerCase()))
                    score += 10;
                else if (!this.job.getData(JobListing.REQUIREMENTS).equals("") &&
                        this.job.getData(JobListing.REQUIREMENTS).toString().toLowerCase().contains(word.toLowerCase()))
                    score += 10;
                else if (!this.job.getData(JobListing.APPLICATION_REQUIREMENTS).equals("") &&
                        this.job.getData(JobListing.APPLICATION_REQUIREMENTS).toString().toLowerCase().contains(word.toLowerCase()))
                    score += 10;
                else if (!this.job.getData(JobListing.DESCRIPTION).equals("") &&
                        this.job.getData(JobListing.DESCRIPTION).toString().toLowerCase().contains(word.toLowerCase()))
                    score += 10;

            }

            this.score += score;

        }


    }
}
