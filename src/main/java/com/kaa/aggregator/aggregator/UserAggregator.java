package com.kaa.aggregator.aggregator;

import com.kaa.aggregator.config.DbConfig;
import com.kaa.aggregator.model.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAggregator {

    private final DbConfig dbConfig;
    private final Map<DbConfig.Source, NamedParameterJdbcTemplate> templates = new HashMap<>();

    public UserAggregator(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @PostConstruct
    private void init() {
        dbConfig.dataSources.forEach(source -> {
            var dataSource = new DriverManagerDataSource();
            dataSource.setUrl(source.getUrl());
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUsername(source.getUser());
            dataSource.setPassword(source.getPassword());
            templates.put(source, new NamedParameterJdbcTemplate(dataSource));
        });
    }

    public Collection<UserDto> aggregateUsers() {
        var users = new ArrayList<UserDto>();
        templates.forEach((key, template) ->
                users.addAll(template.query(prepareGetUsersRequest(key), getMapper(key.getMapping()))));
        return users;
    }

    private RowMapper<UserDto> getMapper(Map<String, String> mapping) {
        return (rs, rowNum) -> {
            var user = new UserDto();
            user.setId(rs.getInt(mapping.get("id")));
            user.setUsername(rs.getString(mapping.get("username")));
            user.setName(rs.getString(mapping.get("name")));
            user.setSurname(rs.getString(mapping.get("surname")));
            return user;
        };
    }

    private String prepareGetUsersRequest(DbConfig.Source source) {
        var sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT").append(" ");
        source.getMapping().values().forEach(mapping -> sqlBuilder.append(mapping).append(","));
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        sqlBuilder.append(" ").append("FROM").append(" ").append(source.getTable());
        return sqlBuilder.toString();
    }

}
