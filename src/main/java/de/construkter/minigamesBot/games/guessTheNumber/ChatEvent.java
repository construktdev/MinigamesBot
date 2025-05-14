package de.construkter.minigamesBot.games.guessTheNumber;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (GuessTheNumber.active_games.containsKey(event.getAuthor())) {
            GuessTheNumber.checkGuess(event.getAuthor(), event.getMessage().getContentRaw(), event.getMessage());
        }
    }
}
