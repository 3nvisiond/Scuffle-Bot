package net.scuffle.scufflebot.entity.filter;

import net.dv8tion.jda.api.entities.ISnowflake;
import net.scuffle.scufflebot.logging.Action;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

import java.util.concurrent.atomic.AtomicLong;

public record Censor(String name, Action action) implements ISnowflake {
    private static final AtomicLong counter = new AtomicLong(0);

    public String name() {
        return this.name;
    }

    public Action actionResult() {
        return this.action;
    }

    @Override
    public @NotNull String getId() {
        return ISnowflake.super.getId();
    }

    @Override
    public long getIdLong() {
        return System.currentTimeMillis() + counter.incrementAndGet();
    }

    @Override
    public @NotNull OffsetDateTime getTimeCreated() {
        return ISnowflake.super.getTimeCreated();
    }
}
