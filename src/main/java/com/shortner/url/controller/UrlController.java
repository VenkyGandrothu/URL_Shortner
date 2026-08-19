package com.shortner.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shortner.url.entity.Url;
import com.shortner.url.service.UrlService;

@RestController
@RequestMapping("/api/v1")
public class UrlController {


    @Autowired
    private UrlService urlService;
    
    @PostMapping("/save")
    public Url saveUrl(@RequestBody Url url){
        return urlService.saveUrl(url);
    }
}
