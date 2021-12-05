package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class handler_incentive extends handler_ext {

    public handler_incentive(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /* Calculates score given to a user based on the user's incentive. */
    public void score_calculate(){
        ArrayList<String> user_incentive = (ArrayList<String>) this.user.getData(User.AWARDS);
        double incentive_score = score_calc_match(user_incentive, 15);
        this.score += incentive_score;
    }


}
