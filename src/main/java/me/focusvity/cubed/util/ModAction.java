package me.focusvity.cubed.util;

import lombok.Getter;

public enum ModAction
{

    MEMBER_ROLE_ADD("Member Role Added"),
    MEMBER_ROLE_REMOVE("Member Role Removed"),
    MEMBER_BANNED("Member Banned"),
    MEMBER_KICKED("Member Kicked");

    @Getter
    private final String name;

    ModAction(String name)
    {
        this.name = name;
    }
}
