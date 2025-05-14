package de.construkter.minigamesBot.games.guessTheNumber;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NumberCommand {
    public static void handle(SlashCommandInteractionEvent event) {
        event.replyEmbeds(GuessTheNumber.getStartEmbed(event.getUser()).build()).queue();
    }
}
