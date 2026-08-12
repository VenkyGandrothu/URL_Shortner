package com.shortner.url.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.shortner.url.entity.Url;
import com.shortner.url.repository.UrlRepo;

@Service
public class UrlService {

    @Autowired
    private UrlRepo urlrepo;

    public Url saveUrl(Url url){
        return urlrepo.save(url);
    }

    public Optional<Url> findShortUrl(String shortCode){
        return urlrepo.findByShortUrl(shortCode);
    }

    public Optional<Url> findLongUrl(String longUrl){
        return urlrepo.findByLongUrl(longUrl);
    }
}
