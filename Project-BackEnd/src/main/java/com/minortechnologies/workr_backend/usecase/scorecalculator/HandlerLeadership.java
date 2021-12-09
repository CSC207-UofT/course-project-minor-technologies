package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.entities.user.Experience;

import java.time.LocalDate;
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
        double score = 0.0;
        ArrayList<Experience> user_leadership_exp = (ArrayList<Experience>) this.user.getData(User.LEADERSHIP);
        if(user_leadership_exp != null){
            for(Experience lead_experience: user_leadership_exp){
                LocalDate start_date = (LocalDate) lead_experience.getData(Experience.START_TIME);
                LocalDate end_date = (LocalDate) lead_experience.getData(Experience.END_TIME);
                String exp_title = (String) lead_experience.getData(Experience.EXPERIENCE_TITLE);
                ArrayList<String> lead_description = (ArrayList<String>)
                        lead_experience.getData(Experience.EXPERIENCE_DESCRPTION);
                double lead_exp_score = scoreTime(start_date, end_date);
                score += lead_exp_score * lead_description.size();
                score += titleScore(exp_title);
            }
        }
        this.score += score;

    }

}
