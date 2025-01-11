package com.ltfullstack.discoverserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController()
@RequestMapping("/api/v1/discovery")
public class DiscoveryController{
    @GetMapping()
    public String welcome() {
        return "Welcome Lap Trinh Full Stack update";
    }
    
}