package de.construkter.minigamesBot.utils;

import de.construkter.minigamesBot.Main;

public class Debug {
    public static void log(String message) {
        if (Main.BOT_TYPE == BotType.DEBUG) {
            System.out.println("[DEBUG] " + message);
        }
    }
}
