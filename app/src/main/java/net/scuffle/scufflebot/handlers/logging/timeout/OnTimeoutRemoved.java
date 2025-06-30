package net.scuffle.scufflebot.handlers.logging.timeout;

import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateTimeOutEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnTimeoutRemoved extends ListenerAdapter {
    @Override
    public void onGuildMemberUpdateTimeOut(GuildMemberUpdateTimeOutEvent event) {
        if (event.getNewTimeOutEnd().getSecond() == 0) {

        }
    }
}