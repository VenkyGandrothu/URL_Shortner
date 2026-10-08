package com.shortner.url.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LongUrlResponseDTO implements Serializable {
    private Long id;
    private String longUrl;
}
