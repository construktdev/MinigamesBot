package de.construkter.minigamesBot.listeners;

import de.construkter.minigamesBot.games.guessTheNumber.GuessTheNumber;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LeaderboardCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("leaderboard")) return;
        if (event.getSubcommandName() == null) {
            event.reply("Please use the correct subcommands!").setEphemeral(true).queue();
            return;
        }
        switch (event.getSubcommandName()) {
            case "guess" -> {
                event.replyEmbeds(GuessTheNumber.getTop10Leaderboard().build()).queue();
                break;
            }
        }
    }
}
