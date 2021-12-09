package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.Experience;
import com.minortechnologies.workr_backend.entities.user.User;

import java.time.LocalDate;
import java.time.Period;

abstract class HandlerExtWork extends Handler{
    public HandlerExtWork(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates a score based on how much time each work experience (related or unrelated)
     * lasts for and gives it a score with more recent experiences given higher weightage.*/
    double scoreTime(Experience experience) {

        LocalDate start_date = (LocalDate) experience.getData(Experience.START_TIME);
        LocalDate end_date = (LocalDate) experience.getData(Experience.END_TIME);
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
    double titleScore(Experience experience){
        String title = (String) experience.getData(Experience.EXPERIENCE_TITLE);
        double score = 0.0;
        if(title != null){
            String[] list_of_words = title.split("");
            for(String word: list_of_words){
                if (!this.job.getData(JobListing.QUALIFICATIONS).equals("") &&
                        this.job.getData(JobListing.QUALIFICATIONS).toString().toLowerCase().contains(word.toLowerCase()))
                    score = 20;
                else if (!this.job.getData(JobListing.REQUIREMENTS).equals("") &&
                        this.job.getData(JobListing.REQUIREMENTS).toString().toLowerCase().contains(word.toLowerCase()))
                    score = 20;
                else if (!this.job.getData(JobListing.APPLICATION_REQUIREMENTS).equals("") &&
                        this.job.getData(JobListing.APPLICATION_REQUIREMENTS).toString().toLowerCase().contains(word.toLowerCase()))
                    score = 20;
                else if (!this.job.getData(JobListing.DESCRIPTION).equals("") &&
                        this.job.getData(JobListing.DESCRIPTION).toString().toLowerCase().contains(word.toLowerCase()))
                    score = 20;
            }
        }
        return score;
    }

    @Override
    abstract public void scoreCalculate();
}
