package net.scuffle.scufflebot;

import java.util.Collection;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.scuffle.scufflebot.interactions.command.Command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Scuffle {
    private static final Logger LOG = LoggerFactory.getLogger(Scuffle.class.getName());

    private static final String TOKEN;

    static {
        final DotenvBuilder dotenv = Dotenv.configure();

        dotenv.directory("./src/main/resources/assets");

        Dotenv dotEnv = dotenv.load();

        TOKEN = dotEnv.get("TOKEN");

        LOG.debug("Receiving TOKEN from .env");
    }

    private static ShardManager shardManager;

    public static void Initiate() {
        final Collection<GatewayIntent> gatewayIntents = List.of(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MODERATION,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.SCHEDULED_EVENTS,
                GatewayIntent.GUILD_WEBHOOKS,
                GatewayIntent.GUILD_PRESENCES);

        LOG.debug(
                "Adding Gateway Intents: guild_members, guild_moderation, guild_emojis_and_stickers, guild_messages" +
                        "guild_message_reactions, message_content, " +
                        "scheduled_events, guild_webhooks, and guild_presences");

        final Collection<CacheFlag> cacheFlags = List.of(
                CacheFlag.ONLINE_STATUS,
                CacheFlag.ACTIVITY,
                CacheFlag.FORUM_TAGS,
                CacheFlag.SCHEDULED_EVENTS,
                CacheFlag.CLIENT_STATUS,
                CacheFlag.VOICE_STATE,
                CacheFlag.MEMBER_OVERRIDES);

        LOG.debug("Adding Cache Flags: online_status, activity, forum_tags, scheduled_events, " +
                "client_status, voice_state, and member_overrides");

        shardManager = DefaultShardManagerBuilder.createDefault(TOKEN).setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.listening("To Envisiond Ramble")).enableIntents(gatewayIntents)
                .enableCache(cacheFlags).addEventListeners(new Command()).build();

        LOG.debug("Building shard with given token, a total of {} Gateway Intents and {} Cache Flags",
                gatewayIntents.size(), cacheFlags.size());
    }

    public static void main(String[] args) {
        Initiate();

        LOG.info("The bot has successfully been connected to discord's servers");
    }

    public static ShardManager getShardManager() {
        return shardManager;
    }
}