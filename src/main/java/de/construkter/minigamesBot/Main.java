package de.construkter.minigamesBot;

import de.construkter.minigamesBot.listeners.ReadyListener;
import de.construkter.minigamesBot.utils.BotType;
import de.construkter.minigamesBot.utils.Debug;
import de.construkter.minigamesBot.utils.RAM;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

public class Main extends Debug {

    private static final Properties config = new Properties();
    private final static String CONFIG_FILE = "bot.properties";
    public static BotType BOT_TYPE = null;
    public static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("[+] Loading config");
        loadConfig();

        LOGGER.info("[+] Starting bot");
        String argument = args.length > 0 ? args[0] : "";
        
        switch (argument) {
            case "-dev" -> BOT_TYPE = BotType.DEV;
            case "-debug" -> BOT_TYPE = BotType.DEBUG;
            case "-invite" -> BOT_TYPE = null;
            default -> BOT_TYPE = BotType.PROD;
        }
        log("Running in Debug-Mode");
        JDABuilder builder = JDABuilder.createDefault(config.getProperty("token"));
        log("JDABuilder created");


        JDA jda = initListeners(builder).build();
        log("JDA built");

        if (BOT_TYPE == null) {
            LOGGER.info(jda.getInviteUrl());
            System.exit(0);
        }
    }

    private static void loadConfig() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            config.load(fis);
            LOGGER.info("[+] Config loaded");
        } catch (Exception e) {
            LOGGER.info("[-][err] Failed to load config");
            System.exit(1);
        }
    }

    private static JDABuilder initListeners(JDABuilder builder) {
        log("Registering listeners");
        builder.addEventListeners(new ReadyListener());
        return builder;
    }

    public static Properties getConfig() {
        return config;
    }
}