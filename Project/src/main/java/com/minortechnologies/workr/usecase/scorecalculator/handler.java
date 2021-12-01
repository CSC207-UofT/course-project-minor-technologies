package com.minortechnologies.workr.usecase.scorecalculator;


import com.minortechnologies.workr.entities.listing.JobListing;
import com.minortechnologies.workr.entities.user.User;

abstract class handler {
    handler next;
    User user;
    JobListing job;
    double score;

    /** Constructs a new handler that processes and calculates a score of a user relative to a job listing
     *
     * @param user_input a user
     * @param job_input a job listing
     * */
    public handler(User user_input, JobListing job_input){
        this.next = null; this.user = user_input; this.job=job_input; this.score = 0.0d;
    }

    public void set_next(handler handler_input){
        this.next = handler_input;
    }

    public double get_score(){ return this.score;}

    /** Calculates a score by running the score_calculate method which is different across the three handlers*/
    void process_request(){
        score_calculate();
        if (next != null){
            next.process_request();this.score = this.score + next.get_score();}

    }

    public abstract void score_calculate();

}
