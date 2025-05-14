package de.construkter.minigamesBot.games.guessTheNumber;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.awt.*;
import java.time.Instant;
import java.util.HashMap;

import static de.construkter.minigamesBot.utils.Debug.log;

public class GuessTheNumber {

    public static HashMap<User, Integer> active_games = new HashMap<>();
    public static HashMap<User, Integer> guesses = new HashMap<>();

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

    public static void startGame(User player) {
        int number = genNumber();
        active_games.put(player, number);
    }

    public static void checkGuess(User user, String guess, Message message) {
        int num = 0;
        try {
            num = Integer.parseInt(guess);
        } catch (NumberFormatException e) {
            message.addReaction(Emoji.fromUnicode("❌")).queue();
            guesses.put(user, guesses.getOrDefault(user, 0) + 1);
        }

    }

    private static int genNumber() {
        return (int) (Math.random() * 10) + 1;
    }
}
