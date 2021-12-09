package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class HandlerAwards extends Handler {

    /** Constructor of the class HandlerAwards
     *
     * @param user_input a user
     * @param job_input a job listing
     * */
    public HandlerAwards(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    /** Calculates score given to a user based on the user's awards. */

    @Override
    public void scoreCalculate(){
        double score = 0.0;
        ArrayList<String> user_awards = (ArrayList<String>) this.user.getData(User.AWARDS);
        if(user_awards != null){
            for(String award: user_awards){
                int counter = 1;
                String[] award_words = award.split(" ");
                /* Words on the following website https://webapps.towson.edu/ows/prepositions.htm were
                considered to create words_avoiding below */
                String[] words_avoiding = {"about", "above", "across", "after", "against", "along", "among", "around",
                        "at", "because", "before", "behind", "below", "beneath", "beside", "besides", "between", "beyond",
                        "but", "by", "concerning", "despite", "down", "during", "except", "excepting", "for", "from", "in",
                        "inside", "instead", "into", "like", "near", "of", "off", "on", "onto", "out", "outside",
                        "over", "past", "regarding", "since", "through", "throughout", "to", "toward", "under", "underneath",
                        "until", "up", "upon", "with", "within", "without", "regard"};
                ArrayList<String> words_to_avoid = new ArrayList<>(Arrays.asList(words_avoiding));
                for(String sentence: award_words){
                    if(sentence.equals(""))
                        continue;
                    String [] list_of_words = sentence.split(" ");

                    for(String word: list_of_words)
                    {
                        if (!words_to_avoid.contains(word.toLowerCase())){
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
                    }

                }
                score += counter * 3;
            }
        }
        this.score += score;
    }

}