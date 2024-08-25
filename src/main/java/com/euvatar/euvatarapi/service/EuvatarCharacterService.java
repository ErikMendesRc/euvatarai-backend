package com.euvatar.euvatarapi.service;

import com.euvatar.euvatarapi.dto.request.CreateCharacterRequest;
import com.euvatar.euvatarapi.dto.request.GetCharacterDetailsRequest;
import com.euvatar.euvatarapi.dto.request.InteractWithCharacterRequest;
import com.euvatar.euvatarapi.dto.request.UpdateCharacterRequest;
import com.euvatar.euvatarapi.dto.response.CreateCharacterResponse;
import com.euvatar.euvatarapi.dto.response.InteractWithCharacterResponse;
import reactor.core.publisher.Mono;

public interface EuvatarCharacterService {
    Mono<CreateCharacterResponse> createCharacter(CreateCharacterRequest request);
    Mono<String> updateCharacter(UpdateCharacterRequest request);
    Mono<String> getCharacterDetails(GetCharacterDetailsRequest request);
    Mono<InteractWithCharacterResponse> interactWithCharacter(InteractWithCharacterRequest request);
}