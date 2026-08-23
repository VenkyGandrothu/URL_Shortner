package com.shortner.url.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shortner.url.dto.CreateUrlResult;
import com.shortner.url.dto.LongUrlResponseDTO;
import com.shortner.url.entity.Url;
import com.shortner.url.exception.ResourceNotFoundException;
import com.shortner.url.repository.UrlRepository;

import jakarta.transaction.Transactional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public CreateUrlResult createUrl(Url url) {

        // 1) Ask DB: does this long URL already exist?
        Optional<Url> existing = urlRepository.findByLongUrl(url.getLongUrl());

        // 2) If yes → don't save again; created=false → controller returns 200
        if (existing.isPresent()) {
            return new CreateUrlResult(existing.get(), false);
        }

        // 3) Create row — DB may still require short_code NOT NULL, so use a temp code first
        url.setCreatedAt(LocalDateTime.now());
        url.setUpdatedAt(LocalDateTime.now());
        url.setClickCount(0L);
        url.setShortCode(temporaryShortCode());

        Url savedUrl = urlRepository.saveAndFlush(url);

        // 4) Replace temp code with Base62(id)
        savedUrl.setShortCode(generateShortCode(savedUrl.getId()));
        Url finalUrl = urlRepository.save(savedUrl);

        return new CreateUrlResult(finalUrl, true);
    }

    /** Unique placeholder so the first INSERT satisfies NOT NULL on short_code. */
    private String temporaryShortCode() {
        return "_" + UUID.randomUUID().toString().replace("-", "").substring(0, 9);
    }

    public LongUrlResponseDTO findByShortCode(String shortCode) {
         Url urlobj = urlRepository.findByShortCode(shortCode).orElseThrow(() -> new ResourceNotFoundException("Short code not found: " + shortCode));
         LongUrlResponseDTO longUrlResponseDTO = new LongUrlResponseDTO();
         longUrlResponseDTO.setId(urlobj.getId());
         longUrlResponseDTO.setLongUrl(urlobj.getLongUrl());
         return longUrlResponseDTO;
    }

    public Optional<Url> findByLongUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl);
    }

    public String generateShortCode(Long urlId){
        return base62Encode(urlId);
    }

    //short code generation functionality
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String base62Encode(Long number){
        if(number == null || number < 0){
            throw new IllegalArgumentException("id must be a non-negative number");
        }
        if(number == 0){
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        while(number > 0){
            int remainder = (int) (number % 62);
            sb.append(BASE62.charAt(remainder));
            number = number / 62;
        }
        return sb.reverse().toString();
    }

}
