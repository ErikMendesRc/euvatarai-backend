package com.euvatar.euvatarapi.dto.request;

import lombok.Data;

@Data
public class CreateCharacterRequest {
    private String charName;
    private String voiceType;
    private String backstory;
    private String actions; // Pode ser uma lista de strings dependendo do uso
}