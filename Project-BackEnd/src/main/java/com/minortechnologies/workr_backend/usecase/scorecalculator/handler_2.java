package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.entities.user.Experience;

import java.time.LocalDate;
import java.util.ArrayList;

public class handler_2 extends handler_ext_work{


    /** Constructor of the class handler_2
     *
     * @param user_input a user
     * @param job_input a job listing
     * */
    public handler_2(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates a score given to user based on the user's related work experiences.*/
    @Override
    public void scoreCalculate() {
        double score = 0.0;
        ArrayList<Experience> user_experiences = (ArrayList<Experience>) this.user.getData(User.REL_WORK_EXP);
        if(user_experiences != null){
            for(Experience experience : user_experiences) {
                LocalDate start_date = (LocalDate) experience.getData(Experience.START_TIME);
                LocalDate end_date = (LocalDate) experience.getData(Experience.END_TIME);
                String title = (String) experience.getData(Experience.EXPERIENCE_TITLE);
                ArrayList<String> description = (ArrayList<String>) experience.getData(Experience.EXPERIENCE_DESCRPTION);
                double experience_score = scoreTime(start_date, end_date);
                score += experience_score * description.size();
            }
        }

        this.score += score;
    }

}



