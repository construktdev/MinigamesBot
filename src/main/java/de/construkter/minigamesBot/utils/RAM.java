package de.construkter.minigamesBot.utils;

import de.construkter.minigamesBot.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RAM {

    public static String BOT_NAME;
    public static TextChannel LOG;

    public static void load(JDA api) {
        BOT_NAME = api.getSelfUser().getName();
        LOG = api.getTextChannelById("1372156418081292308");
    }
}
