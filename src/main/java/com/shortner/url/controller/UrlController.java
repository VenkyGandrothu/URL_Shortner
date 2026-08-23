package com.shortner.url.controller;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shortner.url.dto.CreateUrlResult;
import com.shortner.url.dto.LongUrlResponseDTO;
import com.shortner.url.entity.Url;
import com.shortner.url.service.UrlService;

import jakarta.validation.Valid;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/v1/urls")
    public ResponseEntity<Url> createUrl(@Valid @RequestBody Url url) {
        CreateUrlResult result = urlService.createUrl(url);

        // New row → 201 Created | Already existed → 200 OK
        HttpStatus status = result.isCreated() ? HttpStatus.CREATED : HttpStatus.OK;
        return ResponseEntity.status(status).body(result.getUrl());
    }

    @GetMapping("/api/v1/urls/{shortCode}")
    public LongUrlResponseDTO getByShortCode(@PathVariable String shortCode) {
        return urlService.findByShortCode(shortCode);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Url> redirectToLongUrl(@PathVariable String shortCode) {
        LongUrlResponseDTO longUrlResponseDTO = urlService.findByShortCode(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, longUrlResponseDTO.getLongUrl()).build();
    }
    @GetMapping("/api/v1/urls")
    public ResponseEntity<Url> getByLongUrl(@RequestParam String longUrl) {
        Optional<Url> url = urlService.findByLongUrl(longUrl);
        return url.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
