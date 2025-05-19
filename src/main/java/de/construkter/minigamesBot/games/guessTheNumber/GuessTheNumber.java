package de.construkter.minigamesBot.games.guessTheNumber;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static de.construkter.minigamesBot.utils.Debug.log;

public class GuessTheNumber {

    public static HashMap<User, Integer> active_games = new HashMap<>();
    public static HashMap<User, Integer> guesses = new HashMap<>();
    private static final String LEADERBOARD_FILE = "leaderboard.txt";

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
        // debug
        int number = genNumber();
        System.out.println(number);
        active_games.put(player, number);
    }

    public static void checkGuess(User user, String guess, Message message) {
        int num = 0;
        try {
            num = Integer.parseInt(guess);
        } catch (NumberFormatException e) {
            message.addReaction(Emoji.fromUnicode("❌")).queue();
            guesses.put(user, guesses.getOrDefault(user, 0) + 1);
            message.reply("This is not a valid number! Please enter a number between 1 and 10. This counts as one attempt!").queue();
            checkGameOver(user, message);
            return;
        }

        guesses.put(user, guesses.getOrDefault(user, 0) + 1);

        if (num == active_games.get(user)) {
            message.addReaction(Emoji.fromUnicode("✅")).queue();
            message.reply("Congratulations! You have guessed the right number: " + num +
                         "\nYou have used " + guesses.get(user) + " attempts.\n").queue();

            updateLeaderboard(user);

            active_games.remove(user);
            guesses.remove(user);
        } else {
            message.addReaction(Emoji.fromUnicode("❌")).queue();
            checkGameOver(user, message);
        }
    }

    private static void checkGameOver(User user, Message message) {
        if (guesses.getOrDefault(user, 0) >= 3) {
            message.reply("Too bad! You have used up all 3 attempts. The number you were looking for was: " +
                          active_games.get(user) + ".").queue();
            active_games.remove(user);
            guesses.remove(user);
        }
    }

    private static int genNumber() {
        return (int) (Math.random() * 10) + 1;
    }

    private static void updateLeaderboard(User user) {
        log("Updating leaderboard for user: " + user.getName());
        try {
            File file = new File(LEADERBOARD_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }

            HashMap<String, Integer> leaderboard = readLeaderboard();

            String userId = user.getId();
            leaderboard.put(userId, leaderboard.getOrDefault(userId, 0) + 1);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
                for (Map.Entry<String, Integer> entry : leaderboard.entrySet()) {
                    writer.write(entry.getKey() + ":" + entry.getValue() + ":" + 
                                 user.getName() + System.lineSeparator());
                }
            }
            
            log("Leaderboard updated successfully");
        } catch (IOException e) {
            log("Error updating leaderboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static HashMap<String, Integer> readLeaderboard() {
        HashMap<String, Integer> leaderboard = new HashMap<>();
        try {
            if (Files.exists(Paths.get(LEADERBOARD_FILE))) {
                List<String> lines = Files.readAllLines(Paths.get(LEADERBOARD_FILE));
                for (String line : lines) {
                    String[] parts = line.split(":");
                    if (parts.length >= 2) {
                        String userId = parts[0];
                        int wins = Integer.parseInt(parts[1]);
                        leaderboard.put(userId, wins);
                    }
                }
            }
        } catch (IOException e) {
            log("Error reading leaderboard: " + e.getMessage());
            e.printStackTrace();
        }
        return leaderboard;
    }

    public static EmbedBuilder getTop10Leaderboard() {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Guess the Number - Top 10 Leaderboard")
                .setColor(Color.YELLOW)
                .setTimestamp(Instant.now());
        
        try {
            HashMap<String, Integer> leaderboard = readLeaderboard();
            
            if (leaderboard.isEmpty()) {
                embed.setDescription("No entries on the leaderboard yet!");
                return embed;
            }

            List<Map.Entry<String, Integer>> sortedEntries = leaderboard.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(10)
                    .collect(Collectors.toList());
            
            StringBuilder description = new StringBuilder();
            int rank = 1;

            Map<String, String> userNames = new HashMap<>();
            List<String> lines = Files.readAllLines(Paths.get(LEADERBOARD_FILE));
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length >= 3) {
                    userNames.put(parts[0], parts[2]);
                }
            }

            for (Map.Entry<String, Integer> entry : sortedEntries) {
                String userId = entry.getKey();
                int wins = entry.getValue();
                String username = userNames.getOrDefault(userId, "Unknown user");

                String medal = "";
                if (rank == 1) medal = "🥇 ";
                else if (rank == 2) medal = "🥈 ";
                else if (rank == 3) medal = "🥉 ";
                
                description.append(medal).append("#").append(rank).append(": **")
                          .append(username).append("** - ")
                          .append(wins).append(" successful tries\n");
                
                rank++;
            }
            
            embed.setDescription(description.toString());
            
        } catch (Exception e) {
            log("Error generating leaderboard: " + e.getMessage());
            embed.setDescription("Couldn't load the leaderboard");
            e.printStackTrace();
        }
        
        return embed;
    }
}