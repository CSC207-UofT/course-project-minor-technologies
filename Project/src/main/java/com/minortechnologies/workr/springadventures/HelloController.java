package com.minortechnologies.workr.springadventures;


import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Minor Technologies!";
    }


    @PostMapping("/User/SignIn")
    public String SignIn(@RequestParam String login, @RequestParam String password, @RequestParam boolean remember){
        return null;
    }

    @PostMapping("/User/Signin")
    public String signInByToken(@RequestParam String login, @RequestParam String token){
        return null;
    }
}
