package com.shortner.url.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shortner.url.dto.LongUrlResponseDTO;
import com.shortner.url.entity.Url;
import com.shortner.url.service.UrlService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<Url> createUrl(@Valid @RequestBody Url url) {
        Url savedUrl = urlService.createUrl(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUrl);
    }

    @GetMapping("/{shortCode}")
    public LongUrlResponseDTO getByShortCode(@PathVariable String shortCode) {
        return urlService.findByShortCode(shortCode);
    }

    @GetMapping
    public ResponseEntity<Url> getByLongUrl(@RequestParam String longUrl) {
        Optional<Url> url = urlService.findByLongUrl(longUrl);
        return url.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
