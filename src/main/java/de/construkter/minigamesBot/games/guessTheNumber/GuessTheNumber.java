package de.construkter.minigamesBot.games.guessTheNumber;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;

import static de.construkter.minigamesBot.utils.Debug.log;

public class GuessTheNumber {
    public static EmbedBuilder getStartEmbed(User requester) {
        log("Creating Embed for GuessTheNumber (" + requester.getName() + ")");
        return new EmbedBuilder()
                .setTitle("Guess the Number")
                .setDescription("So " + requester.getAsMention() + ", you really want to play this game?\nOkay that's your choice.\n\n" +
                        "I'm thinking of a number between 1 and 10. I won't tell you higher or lower. You have 3 tries!\n" +
                        "*Type your guess in this chat*")
                .setThumbnail("https://cdn.construkter.de/uploads/x/admin/brave_TCqYRhhKIt.png")
                .setColor(Color.CYAN)
                .setFooter("MinigamesBot | Guess the Number | Requested by " + requester.getName(), requester.getAvatarUrl())
                .setTimestamp(Instant.now());
    }
}
