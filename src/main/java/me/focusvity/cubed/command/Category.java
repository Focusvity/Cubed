package me.focusvity.cubed.command;

import lombok.Getter;

public enum Category
{

    FUN("fun"),
    GUILD("guild"),
    INFORMATION("information"),
    MODERATION("moderation"),
    MUSIC("music"),
    OWNER("owner");

    @Getter
    private final String name;

    Category(String name)
    {
        this.name = name;
    }
}
