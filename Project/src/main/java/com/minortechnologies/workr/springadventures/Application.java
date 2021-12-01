package com.minortechnologies.workr.springadventures;

import java.util.Arrays;


import com.minortechnologies.workr.controllers.localcache.LocalCache;
import com.minortechnologies.workr.controllers.usermanagement.UserManagement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {

    private static LocalCache localCache;
    private static UserManagement userManagement;

    public static LocalCache getLocalCache() {
        return localCache;
    }

    public static UserManagement getUserManagement() {
        return userManagement;
    }

    public static void main(String[] args) {
        localCache = new LocalCache();
        userManagement = new UserManagement();


        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}