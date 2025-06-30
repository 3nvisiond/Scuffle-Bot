package net.scuffle.scufflebot.entity.level;

import net.dv8tion.jda.api.entities.Member;

public interface Experience {
    void removeAll(Member member);

    void remove(Member member, float amount);

    void add(Member member, float amount);

    float getExperience(Member member);

    void setExperience(Member member, float value);
}
