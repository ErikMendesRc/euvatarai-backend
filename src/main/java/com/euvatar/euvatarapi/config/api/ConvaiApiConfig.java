package com.euvatar.euvatarapi.config.api;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class ConvaiApiConfig {

    @Value("${convai.api.base-url}")
    private String baseUrl;

    @Value("${convai.api.key}")
    private String apiKey;

    @Bean
    public String getBaseUrl() {
        return baseUrl;
    }

    @Bean
    public HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
        headers.set("CONVAI-API-KEY", apiKey);
        return headers;
    }

    public StringEntity createStringEntity(String jsonBody) {
        return new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
    }
}