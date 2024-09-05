package com.eazybytes.springboot.cards.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "cards")
@Setter@Getter
public class CardsContactInfoDto {
    private String message;
    private Map<String, String> contactDetails;
}
