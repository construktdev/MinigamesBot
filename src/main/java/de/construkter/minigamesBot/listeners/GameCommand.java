package de.construkter.minigamesBot.listeners;

import de.construkter.minigamesBot.games.guessTheNumber.NumberCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GameCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("game")) return;
        switch (event.getSubcommandName()) {
            case "guess" -> NumberCommand.handle(event);
            case null, default -> event.reply("You have to use the correct subcommands!").setEphemeral(true).queue();
        }
    }
}
