package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.entities.user.Experience;

import java.util.ArrayList;

public class HandlerUnrelatedWork extends HandlerExtWork{

    /** Constructor of the class HandlerUnrelatedWork
     *
     * @param user_input a user
     * @param job_input a job listing
     * */
    public HandlerUnrelatedWork(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates a score given to user based on the user's unrelated work experiences. Unrelated work
     * experiences are generally less important and therefore assigned a lesser weight. */

    @Override
    public void scoreCalculate() {
        ArrayList<Experience> user_experiences = (ArrayList<Experience>) this.user.getData(User.UREL_WORK_EXP);
        this.score = giveScore(user_experiences, 0.5);
    }

}
