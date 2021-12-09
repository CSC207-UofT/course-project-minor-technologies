package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

public class HandlerMain {
    protected User user;
    protected JobListing job;
    double score;

    public HandlerMain(User user_input, JobListing job_input) {
        user = user_input;
        job = job_input;
        this.score=0.0;
    }
    public double getScore(){return this.score;}

    /** Starts the calculation of a user's score relative to a job listing using all three handlers.
     * The first handler uses qualifications, requirements, application requirements, and description,
     * to assign a score. The second and third handlers add to this score by analyzing related and
     * unrelated work experiences of the user, respectively. */

    public void generateScore(){
        Handler h1 = new HandlerSkills(this.user, this.job);
        Handler h2 = new HandlerRelatedWork(this.user, this.job);
        Handler h3 = new HandlerUnrelatedWork(this.user, this.job);
        Handler h4 = new HandlerIncentive(this.user, this.job);
        Handler h5 = new HandlerLeadership(this.user, this.job);
        Handler h6 = new HandlerGrade(this.user, this.job);
        Handler h7 = new HandlerAwards(this.user, this.job);
        h1.setNext(h2);
        h2.setNext(h3);
        h3.setNext(h4);
        h4.setNext(h5);
        h5.setNext(h6);
        h6.setNext(h7);
        h1.processRequest();
        this.score = h1.getScore();
    }
}
