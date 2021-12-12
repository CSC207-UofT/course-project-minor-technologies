package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.entities.user.Experience;

import java.util.ArrayList;

public class HandlerLeadership extends HandlerExtWork {

    /** Constructor of the class HandlerLeadership
     *
     * @param user_input a user
     * @param job_input a job listing
     * */
    public HandlerLeadership(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates score given to a user based on the user's leadership experiences. */

    @Override
    public void scoreCalculate(){
        ArrayList<Experience> user_leadership_exp = (ArrayList<Experience>) this.user.getData(User.LEADERSHIP);
        this.score = give_score(user_leadership_exp, 1);

    }

}
