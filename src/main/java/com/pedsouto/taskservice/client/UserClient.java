package com.pedsouto.taskservice.client;

import com.pedsouto.taskservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user", url = "${user.url}")
public interface UserClient {

    @GetMapping("/user")
    UserDto findUserByEmail(
            @RequestParam("email") String email,
            @RequestHeader("Authorization") String token
    );
}
