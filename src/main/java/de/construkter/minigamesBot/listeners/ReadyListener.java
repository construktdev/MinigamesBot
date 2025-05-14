package de.construkter.minigamesBot.listeners;

import de.construkter.minigamesBot.Main;
import de.construkter.minigamesBot.utils.Debug;
import de.construkter.minigamesBot.utils.RAM;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;

import static de.construkter.minigamesBot.utils.Debug.log;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        RAM.load(event.getJDA());log("RAM loaded");
       log("Received ReadyEvent");
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Ready")
                .setDescription("I am ready to serve you!")
                .setThumbnail("https://cdn.construkter.de/success.png")
                .setColor(Color.GREEN)
                .setFooter("MinigamesBot | Running in " + Main.BOT_TYPE.name() + " Mode")
                .setTimestamp(Instant.now());
        log("Created Embed");
        log(RAM.LOG.toString());
        RAM.LOG.sendMessageEmbeds(embed.build()).queue();
        log("Sent Embed");
    }
}
