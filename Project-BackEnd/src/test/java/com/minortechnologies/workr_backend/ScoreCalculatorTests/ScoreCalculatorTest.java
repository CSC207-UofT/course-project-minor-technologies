package com.minortechnologies.workr_backend.ScoreCalculatorTests;

import com.minortechnologies.workr_backend.entities.listing.CustomJobListing;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.usecase.scorecalculator.*;

import com.minortechnologies.workr_backend.usecase.scorecalculator.handler_1;
import com.minortechnologies.workr_backend.usecase.scorecalculator.handler_2;
import com.minortechnologies.workr_backend.usecase.scorecalculator.handler_3;
import com.minortechnologies.workr_backend.usecase.scorecalculator.handler_main;
import org.junit.*;
import static org.junit.Assert.*;


public class ScoreCalculatorTest {

    User user1;
    CustomJobListing jobl1;

    @Before
    public void setUp() {
        user1 = JobListingDemo.creatingUser();
        jobl1 = JobListingDemo.creatingJobListing();
    }

    @Test
    /* Tests that score_calculate() in handler_main.java computes user's score properly.
     * */
    public void testHandlerMainScoreCalculate(){
        handler_main handler = new handler_main(user1,jobl1);
        handler.generateScore();
        double test_score = handler.getScore();

        assertEquals(test_score, 212.0, 0.01f);
    }

    @Test
    /* Tests that score_calculate() in handler_1.java computes user's score properly.
     *
     */
    public void testHandler1ScoreCalculate() {
        handler_main handler_main1 = new handler_main(user1, jobl1);
        handler_1 handler1 = new handler_1(user1, jobl1);

        handler1.scoreCalculate();
        assertEquals(handler1.getScore(), 40.0,0.01);
    }

    @Test
    /* Tests that score_calculate() in handler_2.java computes user's score properly.
     *
     */
    public void testHandler2ScoreCalculate() {
        handler_2 handler2 = new handler_2(user1, jobl1);

        handler2.scoreCalculate();
        assertEquals(handler2.getScore(), 72.0, 0.01f);
    }

    @Test
    /* Tests that score_calculate() in handler_3.java computes user's score properly.
     *
     */
    public void testHandler3ScoreCalculate() {
        handler_3 handler3 = new handler_3(user1, jobl1);

        handler3.scoreCalculate();
        assertEquals(handler3.getScore(), 26.0, 0.01f);
    }

    @Test
    /* Tests that score_calculate() in handler_leadership.java computes user's score properly.
     *
     */
    public void testHandlerLeadershipScoreCalculate() {
        handler_leadership handlerleadership = new handler_leadership(user1, jobl1);

        handlerleadership.scoreCalculate();
        assertEquals(handlerleadership.getScore(), 30.0, 0.01f);
    }

    @Test
    /* Tests that score_calculate() in handler_incentive.java computes user's score properly.
     *
     */
    public void testHandlerIncentiveScoreCalculate() {
        handler_incentive handlerincentive = new handler_incentive(user1, jobl1);

        handlerincentive.scoreCalculate();
        assertEquals(handlerincentive.getScore(), 15.0, 0.01f);
    }

    @Test
    public void testHandlerAwardsScoreCalculate() {
        handler_awards handlerawards = new handler_awards(user1, jobl1);

        handlerawards.scoreCalculate();
        assertEquals(handlerawards.getScore(), 9.0, 0.01f);
    }

    @Test
    public void testHandlerGpaScoreCalculate() {
        handler_6 handler_gpa= new handler_6(user1, jobl1);

        handler_gpa.scoreCalculate();
        assertEquals(handler_gpa.getScore(), 20.0, 0.01f);
    }


}
