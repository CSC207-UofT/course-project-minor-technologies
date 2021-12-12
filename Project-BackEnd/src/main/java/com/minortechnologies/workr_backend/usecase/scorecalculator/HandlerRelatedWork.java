package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.entities.user.Experience;

import java.time.LocalDate;
import java.util.ArrayList;

public class HandlerRelatedWork extends HandlerExtWork{


    /** Constructor of the class HandlerRelatedWork
     *
     * @param user_input a user
     * @param job_input a job listing
     * */
    public HandlerRelatedWork(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates a score given to user based on the user's related work experiences.*/
    @Override
    public void scoreCalculate() {
        ArrayList<Experience> user_experiences = (ArrayList<Experience>) this.user.getData(User.REL_WORK_EXP);
        this.score = give_score(user_experiences, 1);
    }

}



