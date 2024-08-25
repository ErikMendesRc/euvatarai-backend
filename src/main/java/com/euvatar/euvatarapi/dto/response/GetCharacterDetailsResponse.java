package com.euvatar.euvatarapi.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GetCharacterDetailsResponse {
    private String characterName;
    private String userID;
    private String characterID;
    private String listing;
    private List<String> languageCodes;
    private String voiceType;
    private List<String> characterActions;
    private List<String> characterEmotions;
    private ModelDetails modelDetails;
    private String languageCode;
    private GuardrailMeta guardrailMeta;
    private CharacterTraits characterTraits;
    private String timestamp;
    private int verbosity;
    private String organizationID;
    private boolean isNarrativeDriven;
    private String startNarrativeSectionID;
    private boolean moderationEnabled;
    private List<String> pronunciations;
    private List<String> boostedWords;
    private List<String> allowedModerationFilters;
    private MemorySettings memorySettings;
    private String uncensoredAccessConsent;
    private String nsfwModelSize;
    private String temperature;
    private String backstory;
    private boolean editCharacterAccess;

    // Nested classes for complex fields
    @Data
    public static class ModelDetails {
        private String modelType;
        private String modelLink;
        private String modelPlaceholder;
    }

    @Data
    public static class GuardrailMeta {
        private int limitResponseLevel;
        private List<String> blockedWords;
    }

    @Data
    public static class CharacterTraits {
        private List<String> catchPhrases;
        private String speakingStyle;
        private PersonalityTraits personalityTraits;

        @Data
        public static class PersonalityTraits {
            private int openness;
            private int sensitivity;
            private int extraversion;
            private int agreeableness;
            private int meticulousness;
        }
    }

    @Data
    public static class MemorySettings {
        private boolean enabled;
    }
}