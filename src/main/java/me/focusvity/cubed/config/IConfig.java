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
    @Getter
    @Setter
    private String sqlHost;
    @Getter
    @Setter
    private int sqlPort;
    @Getter
    @Setter
    private String sqlUser;
    @Getter
    @Setter
    private String sqlPassword;
    @Getter
    @Setter
    private String sqlDatabase;
    @Getter
    @Setter
    private String bfdToken;
}
