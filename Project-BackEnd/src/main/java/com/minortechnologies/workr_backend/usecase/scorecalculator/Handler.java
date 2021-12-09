package com.minortechnologies.workr_backend.usecase.scorecalculator;


import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

public abstract class Handler {
    Handler next;
    User user;
    JobListing job;
    double score;

    /** Constructs a new handler that processes and calculates a score of a user relative to a job listing
     *
     * @param user_input a user
     * @param job_input a job listing
     * */
    public Handler(User user_input, JobListing job_input){
        this.next = null; this.user = user_input; this.job=job_input; this.score = 0.0d;
    }
    /** Links the class to the next processing unit to continue the calculation
     *
     * @param handler_input the next handler/processing unit for score calculation
     * */
    public void setNext(Handler handler_input){
        this.next = handler_input;
    }

    /** Sets the calculated score to the class instance variable*/
    public double getScore(){ return this.score;}

    /**Sets a score by running the score_calculate method which is different across the three handlers*/
    void processRequest(){
        scoreCalculate();
        if (next != null){
            next.processRequest();this.score = this.score + next.getScore();}

    }
    /**Abstract method to calculate score*/
    public abstract void scoreCalculate();

}