package com.minortechnologies.workr_backend.framework.networkhandler;


import com.minortechnologies.workr_backend.controllers.backgroundoperations.BackgroundOperations;
import com.minortechnologies.workr_backend.controllers.localcache.LocalCache;
import com.minortechnologies.workr_backend.controllers.usermanagement.AuthTokenController;
import com.minortechnologies.workr_backend.controllers.usermanagement.UserManagement;
import com.minortechnologies.workr_backend.demo.demosource.DemoJobListingSource;
import com.minortechnologies.workr_backend.entities.user.Experience;
import com.minortechnologies.workr_backend.entities.user.User;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;


@SpringBootApplication
public class Application {

    private static LocalCache localCache;
    private static UserManagement userManagement;
    private static AuthTokenController authTokenController;
    private static DemoJobListingSource demoSource;

    public static DemoJobListingSource getDemoSource(){
        return demoSource;
    }

    public static LocalCache getLocalCache() {
        return localCache;
    }

    public static UserManagement getUserManagement() {
        return userManagement;
    }

    private static ConfigurableApplicationContext ctx_test;

    public static ConfigurableApplicationContext getCtx(){
        return ctx_test;
    }

    public static AuthTokenController getAuthTokenController(){
        return authTokenController;
    }

    public static void main(String[] args) {
        creatingUser();

        localCache = new LocalCache();
        userManagement = new UserManagement();
        authTokenController = new AuthTokenController(userManagement);
        demoSource = new DemoJobListingSource();
        BackgroundOperations.startBackgroundLoop();
        BackgroundOperations.setUpdateInterval(60000 * 5);
        ctx_test = SpringApplication.run(Application.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        return args -> {

            System.out.println("Welcome to Workr by MinorTechnologies");
        };
    }

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
        Experience leader_experience1 = new Experience();
        leader_experience1.addData(Experience.EXPERIENCE_TITLE, "Leadership experience in economic analysis project");
        ArrayList<String> leader_exp1_description = new ArrayList<String>();
        leader_exp1_description.add("Helped team members in economic analysis project");
        leader_experience1.addData(Experience.EXPERIENCE_DESCRPTION,leader_exp1_description);
        LocalDate start_date_exp1 = LocalDate.of(2021, 2, 15);
        LocalDate end_date_exp1 = LocalDate.of(2021, 11, 15);
        leader_experience1.addData(Experience.START_TIME, start_date_exp1);
        leader_experience1.addData(Experience.END_TIME, end_date_exp1);

        ArrayList<Experience> leadExp = new ArrayList<>();
        leadExp.add(leader_experience1);
        user_test.addData(User.LEADERSHIP, leadExp);

        user_test.addData(User.LEADERSHIP, leader_experience1);
        ArrayList<String> awards_received = new ArrayList<String>();
        awards_received.add("Economic Analysis Project Award");
        awards_received.add("Certificate on Economic Modelling");
        user_test.addData(User.AWARDS, awards_received);

        Map<String, Object> test = user_test.serialize();
        JSONObject jobject = new JSONObject(test);

        //System.out.println(jobject);

        return user_test;
    }

}