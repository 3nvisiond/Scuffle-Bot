package space.jetbrains.uision.scufflebot.handlers.logging.timeout;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateTimeOutEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public class OnTimeoutRemoved extends ListenerAdapter {
    @Override
    public void onGuildMemberUpdateTimeOut(@NotNull GuildMemberUpdateTimeOutEvent event) {
        if (event.getNewTimeOutEnd().getSecond() == 0) {

        }
    }
}