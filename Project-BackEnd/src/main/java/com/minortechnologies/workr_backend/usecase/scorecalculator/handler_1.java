package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

import java.util.ArrayList;

public class handler_1 extends handler_ext {

    /** Constructor of the class handler_1
     *
     * @param user_input a user
     * @param job_input a job listing
     * */
    public handler_1(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates the score given to a user by adding 10 to the score when skills in the
     * user's skill set are present in the qualifications, requirements, application requirements,
     * and description. */
    @Override
    public void scoreCalculate() {
        ArrayList<String> data = (ArrayList<String>) this.user.getData(User.SKILL_SET);
        double score = scoreMatch(data, 10);
        this.score += score;
    }
}
