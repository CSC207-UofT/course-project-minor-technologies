package com.minortechnologies.workr_backend.ScoreCalculatorTests;

import com.minortechnologies.workr_backend.entities.listing.CustomJobListing;
import com.minortechnologies.workr_backend.entities.user.User;
import com.minortechnologies.workr_backend.usecase.scorecalculator.*;

import com.minortechnologies.workr_backend.usecase.scorecalculator.HandlerSkills;
import com.minortechnologies.workr_backend.usecase.scorecalculator.HandlerRelatedWork;
import com.minortechnologies.workr_backend.usecase.scorecalculator.HandlerUnrelatedWork;
import com.minortechnologies.workr_backend.usecase.scorecalculator.HandlerMain;

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
    /* Tests that score_calculate() in HandlerMain.java computes user's score properly.
     * */
    public void testHandlerMainScoreCalculate(){
        HandlerMain handler = new HandlerMain(user1,jobl1);
        handler.generateScore();
        double test_score = handler.getScore();

        assertEquals(test_score, 212.0, 0.01f);
    }

    @Test
    /* Tests that score_calculate() in HandlerSkills.java computes user's score properly.
     *
     */
    public void testHandlerSkillsScoreCalculate() {
        HandlerSkills handler1 = new HandlerSkills(user1, jobl1);

        handler1.scoreCalculate();
        assertEquals(handler1.getScore(), 40.0,0.01);
    }

    @Test
    /* Tests that score_calculate() in HandlerRelatedWork.java computes user's score properly.
     *
     */
    public void testHandlerRelatedWorkScoreCalculate() {
        HandlerRelatedWork handler2 = new HandlerRelatedWork(user1, jobl1);

        handler2.scoreCalculate();
        assertEquals(handler2.getScore(), 72.0, 0.01f);
    }

    @Test
    /* Tests that score_calculate() in HandlerUnrelatedWork.java computes user's score properly.
     *
     */
    public void testHandlerUnrelatedWorkScoreCalculate() {
        HandlerUnrelatedWork handler3 = new HandlerUnrelatedWork(user1, jobl1);

        handler3.scoreCalculate();
        assertEquals(handler3.getScore(), 26.0, 0.01f);
    }

    @Test
    /* Tests that score_calculate() in HandlerLeadership.java computes user's score properly.
     *
     */
    public void testHandlerLeadershipScoreCalculate() {
        HandlerLeadership handlerleadership = new HandlerLeadership(user1, jobl1);

        handlerleadership.scoreCalculate();
        assertEquals(handlerleadership.getScore(), 30.0, 0.01f);
    }

    @Test
    /* Tests that score_calculate() in HandlerIncentive.java computes user's score properly.
     *
     */
    public void testHandlerIncentiveScoreCalculate() {
        HandlerIncentive handlerincentive = new HandlerIncentive(user1, jobl1);

        handlerincentive.scoreCalculate();
        assertEquals(handlerincentive.getScore(), 15.0, 0.01f);
    }

    @Test
    /* Tests that score_calculate() in HandlerAwards.java computes user's score properly.
     *
     */
    public void testHandlerAwardsScoreCalculate() {
        HandlerAwards handlerawards = new HandlerAwards(user1, jobl1);

        handlerawards.scoreCalculate();
        assertEquals(handlerawards.getScore(), 9.0, 0.01f);
    }

    @Test
    /* Tests that score_calculate() in HandlerGrade.java computes user's score properly.
     *
     */
    public void testHandlerGradeScoreCalculate() {
        HandlerGrade handler_gpa= new HandlerGrade(user1, jobl1);

        handler_gpa.scoreCalculate();
        assertEquals(handler_gpa.getScore(), 20.0, 0.01f);
    }


}
