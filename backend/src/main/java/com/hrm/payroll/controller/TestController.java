package com.hrm.payroll.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "HRM System Running";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(defaultValue = "User") String name) {
        return "Hello, " + name + "! Welcome to the HRM Payroll System.";
    }
}
