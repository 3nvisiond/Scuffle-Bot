package net.scuffle.scufflebot.entity.warn;

import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.Nullable;

public record Warn(Member member, @Nullable String reason) {

}
