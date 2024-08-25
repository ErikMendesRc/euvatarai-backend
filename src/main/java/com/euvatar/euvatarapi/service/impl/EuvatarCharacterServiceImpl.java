package com.euvatar.euvatarapi.service.impl;

import com.euvatar.euvatarapi.config.api.ConvaiApiConfig;
import com.euvatar.euvatarapi.dto.request.CreateCharacterRequest;
import com.euvatar.euvatarapi.dto.request.GetCharacterDetailsRequest;
import com.euvatar.euvatarapi.dto.request.InteractWithCharacterRequest;
import com.euvatar.euvatarapi.dto.request.UpdateCharacterRequest;
import com.euvatar.euvatarapi.dto.response.CreateCharacterResponse;
import com.euvatar.euvatarapi.dto.response.InteractWithCharacterResponse;
import com.euvatar.euvatarapi.service.EuvatarCharacterService;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class EuvatarCharacterServiceImpl implements EuvatarCharacterService {

    private static final Logger logger = LoggerFactory.getLogger(EuvatarCharacterServiceImpl.class);

    private final CloseableHttpClient httpClient;
    private final ConvaiApiConfig convaiApiConfig;

    @Autowired
    public EuvatarCharacterServiceImpl(CloseableHttpClient httpClient, ConvaiApiConfig convaiApiConfig) {
        this.httpClient = httpClient;
        this.convaiApiConfig = convaiApiConfig;
    }

    @Override
    public Mono<CreateCharacterResponse> createCharacter(CreateCharacterRequest request) {
        logger.info("Creating character with request: {}", request);

        try {
            HttpPost postRequest = new HttpPost(convaiApiConfig.getBaseUrl() + "/character/create");
            postRequest.setHeader("Content-Type", convaiApiConfig.getDefaultHeaders().getContentType());
            postRequest.setHeader("CONVAI-API-KEY", convaiApiConfig.getDefaultHeaders().getFirst("CONVAI-API-KEY"));

            String jsonBody = String.format(
                    "{\"charName\":\"%s\",\"voiceType\":\"%s\",\"backstory\":\"%s\"}",
                    request.getCharName(),
                    request.getVoiceType(),
                    request.getBackstory()
            );
            postRequest.setEntity(convaiApiConfig.createStringEntity(jsonBody));

            HttpClientResponseHandler<Mono<CreateCharacterResponse>> responseHandler = this::handleCreateCharacterResponse;

            return httpClient.execute(postRequest, responseHandler);

        } catch (IOException e) {
            logger.error("Error creating character. Request: {}. Error: {}", request, e.getMessage());
            return Mono.error(new RuntimeException("Error creating character", e));
        }
    }

    private Mono<CreateCharacterResponse> handleCreateCharacterResponse(ClassicHttpResponse response) throws IOException, ParseException {
        int status = response.getCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        if (status >= 200 && status < 300) {
            CreateCharacterResponse createResponse = new CreateCharacterResponse();
            createResponse.setCharID(responseBody);  // Ajuste conforme a resposta da API
            return Mono.just(createResponse);
        } else {
            logger.error("Failed to create character. HTTP error code: {}", status);
            return Mono.error(new RuntimeException("Failed to create character: " + responseBody));
        }
    }

    @Override
    public Mono<String> updateCharacter(UpdateCharacterRequest request) {
        logger.info("Updating character with request: {}", request);

        try {
            HttpPost postRequest = new HttpPost(convaiApiConfig.getBaseUrl() + "/character/update");
            postRequest.setHeader("Content-Type", convaiApiConfig.getDefaultHeaders().getContentType());
            postRequest.setHeader("CONVAI-API-KEY", convaiApiConfig.getDefaultHeaders().getFirst("CONVAI-API-KEY"));

            String jsonBody = String.format(
                    "{\"charID\":\"%s\",\"charName\":\"%s\",\"backstory\":\"%s\"}",
                    request.getCharID(),
                    request.getCharName(),
                    request.getBackstory()
            );

            postRequest.setEntity(convaiApiConfig.createStringEntity(jsonBody));

            HttpClientResponseHandler<Mono<String>> responseHandler = response -> handleStringResponse(response, "Failed to update character");

            return httpClient.execute(postRequest, responseHandler);

        } catch (IOException e) {
            logger.error("Error updating character. Request: {}. Error: {}", request, e.getMessage());
            return Mono.error(new RuntimeException("Error updating character", e));
        }
    }

    @Override
    public Mono<String> getCharacterDetails(GetCharacterDetailsRequest request) {
        logger.info("Retrieving character details with request: {}", request);

        try {
            HttpPost postRequest = new HttpPost(convaiApiConfig.getBaseUrl() + "/character/get");
            postRequest.setHeader("Content-Type", convaiApiConfig.getDefaultHeaders().getContentType());
            postRequest.setHeader("CONVAI-API-KEY", convaiApiConfig.getDefaultHeaders().getFirst("CONVAI-API-KEY"));

            String jsonBody = String.format(
                    "{\"charID\":\"%s\"}",
                    request.getCharID()
            );
            postRequest.setEntity(convaiApiConfig.createStringEntity(jsonBody));

            HttpClientResponseHandler<Mono<String>> responseHandler = response -> handleStringResponse(response, "Failed to retrieve character details");

            return httpClient.execute(postRequest, responseHandler);

        } catch (IOException e) {
            logger.error("Error retrieving character details. Request: {}. Error: {}", request, e.getMessage());
            return Mono.error(new RuntimeException("Error retrieving character details", e));
        }
    }

    @Override
    public Mono<InteractWithCharacterResponse> interactWithCharacter(InteractWithCharacterRequest request) {
        logger.info("Interacting with character with request: {}", request);

        try {
            HttpPost postRequest = new HttpPost(convaiApiConfig.getBaseUrl() + "/character/getResponse");
            postRequest.setHeader("Content-Type", convaiApiConfig.getDefaultHeaders().getContentType());
            postRequest.setHeader("CONVAI-API-KEY", convaiApiConfig.getDefaultHeaders().getFirst("CONVAI-API-KEY"));

            String jsonBody = String.format(
                    "{\"charID\":\"%s\",\"userText\":\"%s\",\"sessionID\":\"%s\",\"voiceResponse\":\"%s\",\"audio\":\"%s\",\"sampleRate\":\"%s\"}",
                    request.getCharID(),
                    request.getUserText(),
                    request.getSessionID(),
                    request.isVoiceResponse(),
                    request.getAudio(),
                    request.getSampleRate()
            );
            postRequest.setEntity(convaiApiConfig.createStringEntity(jsonBody));

            HttpClientResponseHandler<Mono<InteractWithCharacterResponse>> responseHandler = this::handleInteractWithCharacterResponse;

            return httpClient.execute(postRequest, responseHandler);

        } catch (IOException e) {
            logger.error("Error interacting with character. Request: {}. Error: {}", request, e.getMessage());
            return Mono.error(new RuntimeException("Error interacting with character", e));
        }
    }

    private Mono<String> handleStringResponse(ClassicHttpResponse response, String errorMessage) throws IOException, ParseException {
        int status = response.getCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        if (status >= 200 && status < 300) {
            return Mono.just(responseBody);
        } else {
            logger.error("{} HTTP error code: {}", errorMessage, status);
            return Mono.error(new RuntimeException(errorMessage + ": " + responseBody));
        }
    }

    private Mono<InteractWithCharacterResponse> handleInteractWithCharacterResponse(ClassicHttpResponse response) throws IOException, ParseException {
        int status = response.getCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        if (status >= 200 && status < 300) {
            InteractWithCharacterResponse interactResponse = new InteractWithCharacterResponse();
            interactResponse.setText(responseBody);
            return Mono.just(interactResponse);
        } else {
            logger.error("Failed to interact with character. HTTP error code: {}", status);
            return Mono.error(new RuntimeException("Failed to interact with character: " + responseBody));
        }
    }
}