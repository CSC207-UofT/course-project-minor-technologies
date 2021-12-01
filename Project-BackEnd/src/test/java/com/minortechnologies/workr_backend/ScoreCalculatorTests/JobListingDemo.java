package com.minortechnologies.workr_backend.ScoreCalculatorTests;

import com.minortechnologies.workr_backend.entities.listing.CustomJobListing;
import com.minortechnologies.workr_backend.entities.listing.JobListing;
import com.minortechnologies.workr_backend.entities.listing.JobType;
import com.minortechnologies.workr_backend.entities.listing.ListingType;
import com.minortechnologies.workr_backend.entities.user.Experience;
import com.minortechnologies.workr_backend.entities.user.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class JobListingDemo {
    /** Used this job listing on LinkedIn to create user and job listing for unit tests:
     * https://www.linkedin.com/jobs/view/2727426318
     */

    /** Creating a job listing for unit tests. */
    public static CustomJobListing creatingJobListing() {
        CustomJobListing jl1 = new CustomJobListing("Analyst, Economic Advisory");
        jl1.addData(JobListing.LISTING_TYPE, ListingType.LINKED_IN);
        jl1.addData(JobListing.LOCATION, "Toronto, ON");
        jl1.addData(JobListing.PAY, "100000");
        jl1.addData(JobListing.JOB_TYPE, JobType.FULL_TIME);
        jl1.addData(JobListing.QUALIFICATIONS, "MS Excel, Access, Word, PowerPoint, STATA, R, MatLab");
        jl1.addData(JobListing.REQUIREMENTS, "economic impact assessments, cost-benefit analysis, economic benchmarking, economic consulting, data analytics, regional economic development, excellent analytical, project management, multi-tasking, communication, organizational, teamwork and interpersonal skills");
        jl1.addData(JobListing.APPLICATION_REQUIREMENTS, "resume, cover letter");
        jl1.addData(JobListing.DESCRIPTION, "You will be responsible for analyzing economic and financial data, market trends, and industry reports to help design, develop, and construct economic models and tools to assist government agencies, non-profits, and corporations across industries and global markets.\n" +
                "You will also be engaged to assist in performing cost-benefit analyses, economic impact assessments, econometric modelling and forecasting, and policy analysis and program design across a range of different sectors.\n" +
                "You may also support in assignments where econometric and statistical techniques are adopted to develop optimization tools, construct valuation and risk assessment models, quantify economic damages, and/or help businesses in their decision-making process. These economic and statistical models are used for a range of applications, including operations and strategic planning, policy design and implementation, project finance, mergers and acquisitions, tax, financial reporting, valuations, and litigation matters.");
        jl1.addData(JobListing.LISTING_DATE, "Nov 5, 2021");
        return jl1;
    }

    /** Creating a user for unit tests. */
    public static User creatingUser() {
        User user_test = new User();
        user_test.addData(User.ACCOUNT_NAME, "Peter");
        ArrayList<String> user_skills = new ArrayList<String>();
        user_skills.add("STATA");
        user_skills.add("R");
        user_skills.add("MS Excel");
        user_test.addData(User.SKILL_SET, user_skills);
        Experience experience1 = new Experience();
        experience1.addData(Experience.EXPERIENCE_TITLE, "Experience in economic consulting");
        ArrayList<String> experience1_description = new ArrayList<String>();
        experience1_description.add("Knowledge of basic economic principles");
        experience1_description.add("Ability to conduct independent economic research");
        experience1.addData(Experience.EXPERIENCE_DESCRPTION, experience1_description);
        LocalDate start_date = LocalDate.of(2020,1,20);
        LocalDate end_date = LocalDate.of(2021, 10, 20);
        experience1.addData(Experience.START_TIME, start_date);
        experience1.addData(Experience.END_TIME, end_date);
        Experience experience2 = new Experience();
        experience2.addData(Experience.EXPERIENCE_TITLE, "Experience in data analysis");
        ArrayList<String> experience2_description = new ArrayList<String>();
        experience2_description.add("Experience working with public data sources");
        experience2.addData(Experience.EXPERIENCE_DESCRPTION, experience2_description);
        LocalDate start_date1 = LocalDate.of(2021,1,20);
        LocalDate end_date1 = LocalDate.of(2021, 10, 20);
        experience2.addData(Experience.START_TIME, start_date1);
        experience2.addData(Experience.END_TIME, end_date1);
        ArrayList<Experience> related_experiences = new ArrayList<Experience>();
        related_experiences.add(experience1);
        related_experiences.add(experience2);
        user_test.addData(User.REL_WORK_EXP, related_experiences);
        Experience unrelated_experience = new Experience();
        unrelated_experience.addData(Experience.EXPERIENCE_TITLE, "Experience working with students");
        ArrayList<String> unrelated_description = new ArrayList<String>();
        unrelated_description.add("Worked as a math tutor at a high school");
        unrelated_experience.addData(Experience.EXPERIENCE_DESCRPTION,unrelated_description);
        LocalDate start_date2 = LocalDate.of(2019,1,20);
        LocalDate end_date2 = LocalDate.of(2021, 10, 20);
        unrelated_experience.addData(Experience.START_TIME, start_date2);
        unrelated_experience.addData(Experience.END_TIME, end_date2);
        ArrayList<Experience> unrelated_experiences = new ArrayList<Experience>();
        unrelated_experiences.add(unrelated_experience);
        user_test.addData(User.UREL_WORK_EXP, unrelated_experiences);
        return user_test;
    }


}
