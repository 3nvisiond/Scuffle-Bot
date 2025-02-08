package space.jetbrains.uision.scufflebot.interactions.command;

import net.dv8tion.jda.api.Permission;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import net.dv8tion.jda.api.events.session.ReadyEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.*;

import java.util.concurrent.TimeUnit;

public class Command extends ListenerAdapter {
    @Override
    @SuppressWarnings({"ConstantValue", "DataFlowIssue"})
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        final String defaultReason = "No reason provided. Set one using /case. (Currently not available)";
        
        try {
            
        } catch (NullPointerException nullException) {
            event.getChannel().sendMessage("The command you used was not able to be executed. " +
                    "We're sorry and are currently working on fixing this issue.").queue();
        }

        
        
        
        switch (event.getName()) {
            case "shutdown": {
                event.deferReply().queue();
                
                int delay = Objects.requireNonNull(event.getOption("wait_time_seconds")).getAsInt();

                try {
                    wait(delay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                event.getJDA().shutdownNow();

                break;
            }
            
            case "help": {


                break;
            }

            case "ping": {
                String ping = event.getJDA().getGatewayPing() + "ms!";

                event.reply("Responded in " + ping).setEphemeral(true).queue();
            }

            case "say": {
                event.deferReply().setEphemeral(true).queue();

                String message = Objects.requireNonNull(event.getOption("message")).getAsString();

                GuildChannelUnion channel = event.getOption("channel").getAsChannel();

                if (channel != null) channel.asTextChannel().sendMessage(message).queue();

                else event.getChannel().sendMessage(message).queue();

                event.getHook().sendMessage("Your message has been sent.").setEphemeral(true).queue();

                break;
            }

            case "kick": {
                event.deferReply().queue();

                Member member = Objects.requireNonNull(event.getOption("member")).getAsMember();

                String reason = Objects.requireNonNull(event.getOption("reason")).getAsString();

                if (reason == null) {
                    reason = defaultReason;
                }

                assert member != null;

                member.kick().reason(reason);

                event.getHook().sendMessage(member.getAsMention() + " has been kicked.").queue();

                break;
            }

            case "ban": {
                event.deferReply().queue();

                Member member = Objects.requireNonNull(event.getOption("member")).getAsMember();

                int duration = event.getOption("duration").getAsInt();

                String reason = event.getOption("reason").getAsString();

                if (reason == null) {
                    reason = defaultReason;
                }

                assert member != null;

                if (duration == Integer.parseInt(null)) {
                    member.ban(0, TimeUnit.DAYS).reason(reason);
                } else {
                    member.ban(duration, TimeUnit.DAYS).reason(reason);
                }

                event.getHook().sendMessage(member.getAsMention() + " has been banned.").queue();

                break;
            }

            case "timeout": {
                event.deferReply().queue();

                Member member = Objects.requireNonNull(event.getOption("member")).getAsMember();
                
                long duration = Objects.requireNonNull(event.getOption("duration")).getAsInt();

                String reason = event.getOption("reason").getAsString();

                if (reason == null) {
                    reason = defaultReason;
                }

                assert member != null;

                assert duration != Integer.parseInt(null);

                member.timeoutFor(duration, TimeUnit.HOURS).reason(reason);

                event.getHook().sendMessage(member.getAsMention() + " has been timed out for "
                        + duration + " hour(s).").queue();

                break;
            }
            
            case "untimeout": {
                event.deferReply().queue();
                
                Member member = Objects.requireNonNull(event.getOption("member")).getAsMember();
                
                String reason = event.getOption("reason").getAsString();
                
                if (reason == null) {
                    reason = defaultReason;
                }

                member.removeTimeout().reason(reason);

                event.getHook().sendMessage("Removed timeout from " + member.getAsMention()).queue();

                break;
            }
            
            case "slowmode": {
                event.deferReply().queue();
                
                GuildChannelUnion channel = Objects.requireNonNull(event.getOption("channel")).getAsChannel();
                
                int value = Objects.requireNonNull(event.getOption("slowmode")).getAsInt();

                channel.asTextChannel().getManager().setSlowmode(value).queue();

                event.getHook().sendMessage("Set the slowmode to " + value).queue();
            }

            case "lockdown": {
                event.deferReply().queue();

                GuildChannelUnion channel = Objects.requireNonNull(event.getOption("channel")).getAsChannel();

                ChannelType channelType = channel.getType();

                Guild guild = event.getGuild();

                switch (channelType) {
                    case TEXT: {
                        channel.asTextChannel().getManager().putPermissionOverride(
                                guild.getPublicRole(),
                                0x00,
                                Permission.MESSAGE_SEND.getRawValue()
                        ).queue();

                        break;
                    }

                    case GUILD_PUBLIC_THREAD, GUILD_PRIVATE_THREAD: {
                        channel.asThreadChannel().getManager().setLocked(true).queue();

                        break;
                    }

                    case FORUM: {
                        channel.asForumChannel().getManager().putPermissionOverride(
                                guild.getPublicRole(),
                                0,
                                Permission.MESSAGE_SEND.getRawValue()
                        ).queue();

                        break;
                    }

                    case VOICE: {
                        channel.asAudioChannel().getManager().putPermissionOverride(
                                guild.getPublicRole(),
                                0,
                                Permission.VOICE_SPEAK.getRawValue()
                        ).queue();

                        break;
                    }
                }

                event.getHook().sendMessage(event.getChannel().getAsMention()
                        + " has been locked by a moderator.").queue();

                break;
            }

            case "setup": {
                event.deferReply().setEphemeral(true).queue();



                event.getHook().sendMessage("The bot has been set up and is ready to use.").setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        List<CommandData> data = new ArrayList<>();

        updateCommandData(data);

        int i = 1;

        for (CommandData command : data) {
            event.getJDA().upsertCommand(command).queue();

            System.out.println("Command #" + i + " " + command.getName());

            i = i + 1;
        }
    }

    private void updateCommandData(List<CommandData> data) {
        data.add(Commands.slash("ping", "Replys with the time it takes for the bot to respond."));

        data.add(Commands.slash("say", "Repeats the message given in the specified channel.")
                .addOption(OptionType.STRING, "message", "The message to be repeated.", true)
                .addOption(OptionType.CHANNEL, "channel", "The channel the message is sent in.", false));

        data.add(Commands.slash("kick", "Kicks the specified user from the server.")
                .addOption(OptionType.USER, "member", "The member to be kicked.", true)
                .addOption(OptionType.STRING, "reason", "The reason to kick.", false)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.KICK_MEMBERS)));

        data.add(Commands.slash("ban", "Bans the specified user from the server.")
                .addOption(OptionType.USER, "member", "The member to be banned.", true)
                .addOption(OptionType.INTEGER, "duration", "The amount of days the member is banned for.", false)
                .addOption(OptionType.STRING, "reason", "The reason to ban.", false)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS)));

        data.add(Commands.slash("timeout", "Mutes the specified user for a duration of time in all channels.")
                .addOption(OptionType.USER, "member", "The member to be muted.", true)
                .addOption(OptionType.INTEGER, "duration", "The duration for the mute.", true)
                .addOption(OptionType.STRING, "reason", "The reason to mute.", false)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL)));

        data.add(Commands.slash("untimeout", "Unmutes the specified user.")
                .addOption(OptionType.USER, "member", "The member to be unmuted.")
                .addOption(OptionType.STRING, "reason", "The reason to unmute.")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL)));

        data.add(Commands.slash("lockdown", "Removes everyone's ability to send messages in the specified channel.")
                .addOption(OptionType.CHANNEL, "channel", "The channel to lock.", true)
                .addOption(OptionType.STRING, "reason", "The reason to lock channels.", false)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL)));

        data.add(Commands.slash("slowmode", "Sets the slowmode of the specified channel.")
                .addOption(OptionType.CHANNEL, "channel", "The channel to set the slowmode to.")
                .addOption(OptionType.INTEGER, "slowmode", "The value to set the slowmode to.")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL)));

        data.add(Commands.slash("help", "Provides instructions on how to use the bot and lists its current commands.")
                .addOption(OptionType.STRING, "command", "The command to provide the description for.", false));

        data.add(Commands.slash("shutdown", "Shutdown the bot safely (DO NOT USE UNLESS YOU KNOW WHAT YOU'RE DOING).")
                .addOption(OptionType.INTEGER, "wait_time_seconds", "Delay.", true)
                .addOption(OptionType.STRING, "reason", "The reason to shutdown.")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER)));

        data.add(Commands.slash("setup", "A classic setup command to get you started on configuration your bot.")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_WEBHOOKS)));
    }
}