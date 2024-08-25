package com.euvatar.euvatarapi.dto.response;

import lombok.Data;

@Data
public class InteractWithCharacterResponse {
    private String text;
    private String audio;
    private String sampleRate;
    private String sessionID;
}
