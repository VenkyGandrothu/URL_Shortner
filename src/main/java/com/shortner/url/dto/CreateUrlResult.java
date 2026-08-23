package com.shortner.url.dto;

import com.shortner.url.entity.Url;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUrlResult {
    private final Url url;
    private final boolean created; // true = brand new, false = already existed
}
