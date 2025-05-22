package de.construkter.minigamesBot.games.roulette;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;

import static de.construkter.minigamesBot.utils.Debug.log;

public class Roulette {
    public static EmbedBuilder getStartEmbed(User requester) {
        log("Creating Embed for Roulette (" + requester.getName() + ")");
        return new EmbedBuilder()
                .setTitle("Roulette")
                .setDescription("So " + requester.getAsMention() + ", you really want to play roulette?\nOkay that's your choice.\n\n" +
                "Place your bet on Red or Black, a number between 0 and 36, or choose Even/Odd.\n" +
                "*Type your bet in this chat*")
                .setThumbnail("https://cdn.construkter.de/uploads/x/admin/brave_HtJvK0cPJd.png")
                .setColor(Color.CYAN)
                .setFooter("MinigamesBot | Guess the Number | Requested by " + requester.getName(), requester.getAvatarUrl())
                .setTimestamp(Instant.now());
    }
}