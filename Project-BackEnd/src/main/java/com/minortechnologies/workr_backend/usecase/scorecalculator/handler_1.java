package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

import java.util.ArrayList;

public class handler_1 extends handler_ext {


    public handler_1(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates the score given to a user by adding 10 to the score when skills in the
     * user's skill set are present in the qualifications, requirements, application requirements,
     * and description. */
    @Override
    public void score_calculate() {
        ArrayList<String> data = (ArrayList<String>) this.user.getData(User.SKILL_SET);
        double score = score_calc_match(data, 10);
        this.score += score;
    }
}
