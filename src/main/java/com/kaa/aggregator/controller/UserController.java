package com.kaa.aggregator.controller;

import com.kaa.aggregator.aggregator.UserAggregator;
import com.kaa.aggregator.model.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserController {

    private final UserAggregator userAggregator;

    public UserController(UserAggregator userAggregator) {
        this.userAggregator = userAggregator;
    }

    @GetMapping("/users")
    public Collection<UserDto> aggregateUsers() {
        return userAggregator.aggregateUsers();
    }
}
