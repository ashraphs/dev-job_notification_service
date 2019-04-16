package com.tng.abt.jobnotificationservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @PostMapping("/job/run/notification")
    public ResponseEntity runJob(){

    }

}
