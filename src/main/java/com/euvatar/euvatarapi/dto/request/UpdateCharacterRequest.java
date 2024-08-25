package com.euvatar.euvatarapi.dto.request;


import lombok.Data;

@Data
public class UpdateCharacterRequest {
    private String charID;
    private String charName;
    private String voiceType;
    private String backstory;
    private String actions;
}
