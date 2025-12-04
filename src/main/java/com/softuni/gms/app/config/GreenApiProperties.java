package com.softuni.gms.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "greenapi")
@Getter
@Setter
public class GreenApiProperties {

    private String idInstance;
    private String tokenInstance;
    private String baseUrl;
}
