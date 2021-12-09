package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

public class HandlerGrade extends Handler{

    /** Constructor of the class HandlerGrade
     *
     * @param user_input a user
     * @param job_input a job listing
     * */
    public HandlerGrade(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates a score given to user based on the user's GPA.  */

    @Override
    public void scoreCalculate() {
        double score = 0.0;
        double gpa;

        if(this.user.getData(User.GPA) != null)
        {
            gpa = Double.parseDouble((String) this.user.getData(User.GPA));

            if(gpa >= 3.7 && gpa <= 4)
                score += 20;
            else if(gpa >= 3.5 && gpa <= 3.7)
                score += 15;
            else if(gpa >= 3.0 && gpa <= 3.5)
                score += 10;
            else if(gpa >= 2.5 && gpa <= 3.0)
                score += 0;
            else
                score -= 10;

            this.score += score;
        }
    }

}
