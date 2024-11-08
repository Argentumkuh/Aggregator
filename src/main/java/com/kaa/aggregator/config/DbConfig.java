package com.kaa.aggregator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "db-config")
public class DbConfig {

    public List<Source> dataSources;

    @Getter
    @Setter
    public static class Source {
        private String name;
        private String strategy;
        private String url;
        private String table;
        private String user;
        private String password;
        private Map<String, String> mapping;
    }
}
