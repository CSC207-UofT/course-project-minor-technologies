package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

import java.util.ArrayList;

abstract class HandlerExt extends Handler{

    public HandlerExt(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates a score based on user's information matching the job listing's information */
    double scoreMatch(ArrayList<String> user_info, double given_score){
        double match_score = 0.0;
        if (user_info != null) {
            for (String sentence : user_info) {
                if (sentence.equals(""))
                    continue;
                String[] list_of_words = sentence.split(" ");
                for (String word : list_of_words) {
                    if (!this.job.getData(JobListing.QUALIFICATIONS).equals("") &&
                            this.job.getData(JobListing.QUALIFICATIONS).toString().toLowerCase().contains(word.toLowerCase()))
                        match_score += given_score;
                    else if (!this.job.getData(JobListing.REQUIREMENTS).equals("") &&
                            this.job.getData(JobListing.REQUIREMENTS).toString().toLowerCase().contains(word.toLowerCase()))
                        match_score += given_score;
                    else if (!this.job.getData(JobListing.APPLICATION_REQUIREMENTS).equals("") &&
                            this.job.getData(JobListing.APPLICATION_REQUIREMENTS).toString().toLowerCase().contains(word.toLowerCase()))
                        match_score += given_score;
                    else if (!this.job.getData(JobListing.DESCRIPTION).equals("") &&
                            this.job.getData(JobListing.DESCRIPTION).toString().toLowerCase().contains(word.toLowerCase()))
                        match_score += given_score;
                }
            }

            }

        return match_score;
    }


    @Override
    abstract public void scoreCalculate();
}