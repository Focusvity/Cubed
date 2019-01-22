package me.focusvity.cubed.command;

import lombok.Getter;

public enum Category
{

    FUN("fun"),
    INFORMATION("information"),
    MUSIC("music"),
    OWNER("owner");

    @Getter
    private final String name;

    Category(String name)
    {
        this.name = name;
    }
}
