package de.construkter.minigamesBot.games.guessTheNumber;

import de.construkter.minigamesBot.utils.Debug;
import de.construkter.minigamesBot.utils.RAM;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class NumberCommand {
    public static void handle(SlashCommandInteractionEvent event) {
        event.replyEmbeds(GuessTheNumber.getStartEmbed(event.getUser()).build()).queue();
        RAM.LOG.sendMessageEmbeds(Debug.getLogEmbed(event.getUser().getAsMention() + " started a new GuessTheNumber Game in " + event.getChannel().getAsMention()).build()).queue();
        GuessTheNumber.startGame(event.getUser());
    }
}