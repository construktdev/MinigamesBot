package de.construkter.minigamesBot.utils;

import de.construkter.minigamesBot.Main;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Debug {
    public static void log(String message) {
        if (Main.BOT_TYPE == BotType.DEBUG) {
            System.out.println("[DEBUG] " + message);
        }
    }

    public static EmbedBuilder getLogEmbed(String message) {
        return new EmbedBuilder()
                .setTitle("Minigames Log")
                .setDescription(message)
                .setColor(Color.GREEN);
    }
}
