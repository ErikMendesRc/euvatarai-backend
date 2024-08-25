package com.euvatar.euvatarapi.dto.request;

import lombok.Data;

@Data
public class InteractWithCharacterRequest {
    private String userText;
    private String charID;
    private String sessionID;
    private boolean voiceResponse;
    private String audio; // Base64 encoded
    private String sampleRate;
}
