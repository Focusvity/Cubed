package me.focusvity.cubed.config;

import lombok.Getter;
import lombok.Setter;

public class IConfig
{

    @Getter
    @Setter
    private String token;
    @Getter
    @Setter
    private String ownerId;
    @Getter
    @Setter
    private String defaultPrefix;
}
