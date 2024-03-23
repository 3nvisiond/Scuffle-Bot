package space.jetbrains.uision.scufflebot;

import space.jetbrains.uision.scufflebot.interactions.command.Command;

import net.dv8tion.jda.api.OnlineStatus;

import net.dv8tion.jda.api.entities.Activity;

import net.dv8tion.jda.api.requests.GatewayIntent;

import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import net.dv8tion.jda.api.utils.cache.CacheFlag;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;

import javax.security.auth.login.LoginException;

import java.util.Collection;
import java.util.List;

public class Scuffle {
    private static final String TOKEN;
    
    static {
        final DotenvBuilder dotenv = Dotenv.configure();
        
        dotenv.directory("./src/main/resources/assets");
        
        Dotenv dotEnv = dotenv.load();
        
        TOKEN = dotEnv.get("TOKEN");
    }
    
    private final ShardManager shardManager;
    
    public Scuffle() throws LoginException {
        final Collection<GatewayIntent> gatewayIntents = List.of(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MODERATION,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.SCHEDULED_EVENTS,
                GatewayIntent.GUILD_WEBHOOKS,
                GatewayIntent.GUILD_PRESENCES
        );
        
        final Collection<CacheFlag> cacheFlags = List.of(
                CacheFlag.ONLINE_STATUS,
                CacheFlag.ACTIVITY,
                CacheFlag.FORUM_TAGS,
                CacheFlag.SCHEDULED_EVENTS,
                CacheFlag.CLIENT_STATUS,
                CacheFlag.VOICE_STATE,
                CacheFlag.MEMBER_OVERRIDES
        );
        
        this.shardManager = DefaultShardManagerBuilder.createDefault(TOKEN).setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.listening("To Envisiond Ramble")).enableIntents(gatewayIntents)
                .enableCache(cacheFlags).addEventListeners(new Command()).build();
    }
    
    public static void  main(String[] args) {
        try {
            new Scuffle();
            
            System.out.println("[+] The bot has successfully been connected to discord's servers.");
        } catch (LoginException exception) {
            System.out.println("[-] The given Token for the current shard was invalid. \n");
            
            exception.printStackTrace();
            
            // Since we can't do anything else, we kill the problem and wait for code changes.
        }
    }
    
    public ShardManager getShardmanager() {
        return this.shardManager;
    }
}