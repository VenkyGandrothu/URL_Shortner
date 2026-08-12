package com.shortner.url.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shortner.url.entity.Url;

@Repository
public interface UrlRepo extends JpaRepository<Url,Long> {
    
    Optional<Url> findByShortUrl(String shortUrl);
    Optional<Url> findByLongUrl(String longUrl);

}