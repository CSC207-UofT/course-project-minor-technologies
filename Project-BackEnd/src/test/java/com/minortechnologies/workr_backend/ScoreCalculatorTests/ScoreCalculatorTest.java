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
    /** Tests that score_calculate() in handler_main.java computes user's score properly.
     * */
    public void testHandlerMainScoreCalculate(){
        handler_main handler = new handler_main(user1,jobl1);
        handler.main();
        double test_score = handler.get_score();

        assertEquals(test_score, 68.0, 0.01f);
    }

    @Test
    /** Tests that score_calculate() in handler_1.java computes user's score properly.
     *
     */
    public void testHandler1ScoreCalculate() {
        handler_main handler_main1 = new handler_main(user1, jobl1);
        handler_1 handler1 = new handler_1(user1, jobl1);

        handler1.score_calculate();
        assertEquals(handler1.get_score(), 30.0,0.01);
    }

    @Test
    /** Tests that score_calculate() in handler_2.java computes user's score properly.
     *
     */
    public void testHandler2ScoreCalculate() {
        handler_2 handler2 = new handler_2(user1, jobl1);

        handler2.score_calculate();
        assertEquals(handler2.get_score(), 32.0, 0.01f);
    }

    @Test
    /** Tests that score_calculate() in handler_3.java computes user's score properly.
     *
     */
    public void testHandler3ScoreCalculate() {
        handler_3 handler3 = new handler_3(user1, jobl1);

        handler3.score_calculate();
        assertEquals(handler3.get_score(), 6.0, 0.01f);
    }

    @Test
    /** Tests that score_calculate() in handler_leadership.java computes user's score properly.
     *
     */
    public void testHandlerLeadershipScoreCalculate() {
        handler_leadership handlerleadership = new handler_leadership(user1, jobl1);

        handlerleadership.score_calculate();
        assertEquals(handlerleadership.get_score(), 10, 0.01f);
    }

    @Test
    /** Tests that score_calculate() in handler_leadership.java computes user's score properly.
     *
     */
    public void testHandlerAwardsScoreCalcualte() {
        handler_awards handlerawards = new handler_awards(user1, jobl1);

        handlerawards.score_calculate();
        assertEquals(handlerawards.get_score(), 69, 0.01f);
    }


}
