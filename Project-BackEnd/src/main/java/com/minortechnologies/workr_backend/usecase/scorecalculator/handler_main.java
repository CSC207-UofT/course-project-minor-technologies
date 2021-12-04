package com.minortechnologies.workr_backend.usecase.scorecalculator;

import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.user.User;

public class handler_main{
    protected User user;
    protected JobListing job;
    double score;

    public handler_main(User user_input, JobListing job_input) {
        user = user_input;
        job = job_input;
        this.score=0.0;
    }
    public double get_score(){return this.score;}

    /** Starts the calculation of a user's score relative to a job listing using all three handlers.
     * The first handler uses qualifications, requirements, application requirements, and description,
     * to assign a score. The second and third handlers add to this score by analyzing related and
     * unrelated work experiences of the user, respectively. */

    public void main(){
        handler h1 = new handler_1(this.user, this.job);
        handler h2 = new handler_2(this.user, this.job);
        handler h3 = new handler_3(this.user, this.job);
        handler h_leadership = new handler_leadership(this.user, this.job);
        handler h_awards = new handler_awards(this.user, this.job);
        h1.set_next(h2);
        h2.set_next(h3);
        h3.set_next(h_leadership);
        h_leadership.set_next(h_awards);
        h1.process_request();
        this.score = h1.get_score();
    }

}
