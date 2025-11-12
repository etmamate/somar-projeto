package br.com.somar.project.somar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    

    @GetMapping("/")
    public String home(){
        return "API Somar - Online!";
    }

    @GetMapping("/health")
    public String health(){
        return "OK!";
    }

}
