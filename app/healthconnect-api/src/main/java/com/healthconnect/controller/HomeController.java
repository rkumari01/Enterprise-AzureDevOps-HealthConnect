
package com.healthconnect.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to HealthConnect";
    }

@GetMapping("/api/version")
public Map<String, String> version() {

    Map<String, String> info = new HashMap<>();

    info.put("application", "HealthConnect");
    info.put("version", "1.0.0");
    info.put("environment", "Development");

    return info;
}


}