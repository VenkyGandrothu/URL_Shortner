package com.shortner.url.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shortner.url.entity.Url;
import com.shortner.url.repository.UrlRepository;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url createUrl(Url url) {
        Url savedUrl = urlRepository.save(url);

        String shortCode = generateShortCode(savedUrl.getId());

        savedUrl.setShortCode(shortCode);
        savedUrl.setCreatedAt(LocalDateTime.now());
        savedUrl.setUpdatedAt(LocalDateTime.now());
        return urlRepository.save(savedUrl);
    }

    public Optional<Url> findByShortCode(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }

    public Optional<Url> findByLongUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl);
    }

    public String generateShortCode(Long urlId){
        return base62Encode(urlId);
    }

    //short code genaration functionality
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
