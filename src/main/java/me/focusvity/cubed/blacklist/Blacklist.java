package me.focusvity.cubed.blacklist;

import lombok.Getter;
import lombok.Setter;

public class Blacklist
{

    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String by;
    @Getter
    @Setter
    private String reason;

    public Blacklist(String id, String by, String reason)
    {
        this.id = id;
        this.by = by;
        this.reason = reason;
    }
}
