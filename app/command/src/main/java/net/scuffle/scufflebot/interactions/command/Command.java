package net.scuffle.scufflebot.interactions.command;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

import net.scuffle.scufflebot.CommandException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Command extends ListenerAdapter {
    private final Logger LOG = LoggerFactory.getLogger(Command.class);

    private int parseToHours(String duration) {
        return Duration.parse(duration).toHoursPart();
    }

    @Contract(value = "null -> true; !null -> false", pure = true)
    private boolean isNull(Object variable) {
        return variable == null ? true : false;
    }

    @Override
    @SuppressWarnings({"ConstantValue", "DataFlowIssue"})
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        final String defaultReason = "No reason provided. Set one using /case. (Currently not available)";
        
        switch (event.getName()) {
            case "shutdown": {
                event.deferReply().queue();

                LOG.warn("Shutting down bot instance, any concurrent information that hasn't been saved will be ignored");

                int delay = event.getOption("wait_time_seconds") != null
                        ? event.getOption("wait_time_seconds").getAsInt() : 0;

                try {
                    wait(delay);
                } catch (InterruptedException e) {
                    throw new CommandException(e);
                }

                event.getJDA().shutdown();

                LOG.info("Bot instance has been shut down");

                break;
            }
            
            case "help": {


                break;
            }

            case "ping": {
                String ping = event.getJDA().getGatewayPing() + "ms!";

                event.reply("Responded in " + ping).setEphemeral(false).queue();
            }

            case "say": {
                event.deferReply().setEphemeral(true).queue();

                String message = event.getOption("message").getAsString();

                if (isNull(message)) event.getChannel().sendMessage("The message context is missing").queue();

                GuildChannelUnion channel = event.getOption("channel").getAsChannel();

                if (channel != null) channel.asTextChannel().sendMessage(message).queue();
                    else event.getChannel().sendMessage(message).queue();

                break;
            }

            case "kick": {
                event.deferReply().queue();

                Member member = Objects.requireNonNull(event.getOption("member")).getAsMember();

                String reason = Objects.requireNonNull(event.getOption("reason")).getAsString();

                if (isNull(reason)) {
                    reason = defaultReason;
                }

                assert member != null;

                member.kick().reason(reason);

                event.getHook().sendMessage(member.getAsMention() + " has been kicked.").queue();

                break;
            }

            case "ban": {
                event.deferReply().queue();

                Member member = event.getOption("member").getAsMember();

                String duration = event.getOption("duration").getAsString();

                String reason = event.getOption("reason").getAsString();

                if (reason == null) {
                    reason = defaultReason;
                }

                assert member != null;

                member.ban(parseToHours(duration), TimeUnit.HOURS).reason(reason).queue();

                event.getHook().sendMessage(member.getAsMention() + " has been banned.").queue();

                break;
            }

            case "timeout": {
                event.deferReply().queue();

                Member member = Objects.requireNonNull(event.getOption("member")).getAsMember();
                
                String duration = Objects.requireNonNull(event.getOption("duration")).getAsString();

                String reason = event.getOption("reason").getAsString();

                if (reason == null) {
                    reason = defaultReason;
                }

                assert member != null;

                member.timeoutFor(parseToHours(duration), TimeUnit.HOURS).reason(reason);

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

                break;
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

                    default: {
                        event.getHook().sendMessage("The channel type is not supported.").queue();

                        break;
                    }
                }

                event.getHook().sendMessage(event.getChannel().getAsMention()
                        + " has been locked by a moderator.").queue();

                break;
            }
        }
    }

    @Override
    public void onReady(ReadyEvent event) {
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
                .addOption(OptionType.STRING, "duration", "The amount of time the member is banned for.", false)
                .addOption(OptionType.STRING, "reason", "The reason to ban.", false)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS)));

        data.add(Commands.slash("timeout", "Mutes the specified user for a duration of time in all channels.")
                .addOption(OptionType.USER, "member", "The member to be muted.", true)
                .addOption(OptionType.STRING, "duration", "The duration for the mute.", true)
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

        data.add(Commands.slash("logs", "Sets up specific channels to send multiple different types of logs to.")
                .addOption(OptionType.CHANNEL, "server", "The server logs channel.", false)
                .addOption(OptionType.CHANNEL, "moderation", "The moderation logs channel.", false)
                .addOption(OptionType.CHANNEL, "message", "The channel to send message based logs to.", false)
                .addOption(OptionType.CHANNEL, "member", "The member logs channel.", false)
                .addOption(OptionType.CHANNEL, "all", "The channel to send all logs to.", false)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER)));
    }
}
