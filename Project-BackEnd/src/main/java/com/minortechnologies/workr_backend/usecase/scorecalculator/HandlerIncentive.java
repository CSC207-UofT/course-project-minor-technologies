package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

import java.util.ArrayList;

public class HandlerIncentive extends HandlerExt {

    /** Constructor of the class HandlerIncentive
     *
     * @param user_input a user
     * @param job_input a job listing
     * */
    public HandlerIncentive(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates score given to a user based on the user's incentive. */
    public void scoreCalculate(){
        ArrayList<String> user_incentive = (ArrayList<String>) this.user.getData(User.INCENTIVE);
        double incentive_score = scoreMatch(user_incentive, 15);
        this.score += incentive_score;
    }


}
