package com.minortechnologies.workr.ScoreCalculatorTests;

import com.minortechnologies.workr.entities.listing.CustomJobListing;
import com.minortechnologies.workr.entities.user.User;
import com.minortechnologies.workr.usecase.scorecalculator.*;

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

        handler1.score_calculate();assertEquals(handler1.get_score(), 30.0,0.01);
    }

    @Test
    public void testHandler2ScoreCalculate() {
        handler_2 handler2 = new handler_2(user1, jobl1);

        handler2.score_calculate();
        assertEquals(handler2.get_score(), 32.0, 0.01f);
    }

    @Test
    public void testHandler3ScoreCalculate() {
        handler_3 handler3 = new handler_3(user1, jobl1);

        handler3.score_calculate();
        assertEquals(handler3.get_score(), 6.0, 0.01f);
    }
}
