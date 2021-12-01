package com.minortechnologies.workr_frontend.usecase.scorecalculator;

import com.minortechnologies.workr_frontend.entities.listing.JobListing;
import com.minortechnologies.workr_frontend.entities.user.User;

import java.time.LocalDate;
import java.time.Period;

abstract class handler_ext_work extends handler{
    public handler_ext_work(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates a score based on how much time each work experience (related or unrelated)
     * lasts for and gives it a score with more recent experiences given higher weightage.*/
    double score_calc_time(LocalDate start_date, LocalDate end_date) {
        LocalDate current_date = LocalDate.now();

        double sub_score = 0.0;
        if(current_date.getYear() - end_date.getYear() == 0)
            sub_score += 9;
        else if(current_date.getYear() - end_date.getYear() == 1)
            sub_score += 7;
        else if(current_date.getYear() - end_date.getYear() == 2)
            sub_score += 5;
        else if (current_date.getYear() - end_date.getYear() > 2)
            sub_score += 3;

        int years = Period.between(start_date, end_date).getYears();
        int months = Period.between(start_date, end_date).getMonths();
        int total_months = years*12 + months;
        if(total_months >= 24)
            sub_score += 3;
        else if(total_months >= 12)
            sub_score += 2;
        else if(total_months >= 6)
            sub_score += 1;
        else
            sub_score += 0.5;
        return sub_score;
    }

    @Override
    abstract public void score_calculate();
}
