package de.construkter.minigamesBot.utils;

public enum BotType {
    PROD,
    DEV,
    DEBUG;

    public static BotType getBotType(String type) {
        return switch (type) {
            case "prod" -> PROD;
            case "dev" -> DEV;
            case "debug" -> DEBUG;
            default -> null;
        };
    }
}
