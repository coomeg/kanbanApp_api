package com.jp.kanbannapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jp.kanbannapp.entity.Authority;
import com.jp.kanbannapp.entity.User;
import com.jp.kanbannapp.service.TestService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/public/users")
    public User users() {
        log.info("TestController()");
        User user = testService.getUser();
        return user;
    }

    @GetMapping("/public/authority")
    public Authority authority() {
        log.info("TestController()");
        Authority user = testService.getAuthority();
        return user;
    }

    @GetMapping("/public/insert/{userId}")
    public User insert(@PathVariable("userId") String userId) {
        log.info("insert()");
        return testService.insertUser(userId);
    }
}
