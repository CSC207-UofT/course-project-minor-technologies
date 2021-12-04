package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

import java.util.ArrayList;

public class handler_awards extends handler {

    public handler_awards(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates score given to a user based on the user's awards. */

    @Override
    public void score_calculate(){
        double score = 0.0;
        ArrayList<String> user_awards = (ArrayList<String>) this.user.getData(User.AWARDS);
        if(user_awards != null){
            for(String award: user_awards){
                int counter = 0;
                String[] award_words = award.split(" ");
                System.out.println(award_words);
                for(String word: award_words){
                    if (!this.job.getData(JobListing.QUALIFICATIONS).equals("") &&
                            this.job.getData(JobListing.QUALIFICATIONS).toString().toLowerCase().contains(word.toLowerCase()))
                        counter += 1;
                    else if (!this.job.getData(JobListing.REQUIREMENTS).equals("") &&
                            this.job.getData(JobListing.REQUIREMENTS).toString().toLowerCase().contains(word.toLowerCase()))
                        counter += 1;
                    else if (!this.job.getData(JobListing.APPLICATION_REQUIREMENTS).equals("") &&
                            this.job.getData(JobListing.APPLICATION_REQUIREMENTS).toString().toLowerCase().contains(word.toLowerCase()))
                        counter += 1;
                    else if (!this.job.getData(JobListing.DESCRIPTION).equals("") &&
                            this.job.getData(JobListing.DESCRIPTION).toString().toLowerCase().contains(word.toLowerCase()))
                        counter += 1;
                }
                score += counter * 3;
            }
        }
        this.score += score;
    }

}
