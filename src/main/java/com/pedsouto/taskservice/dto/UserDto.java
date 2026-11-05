package com.pedsouto.taskservice.dto;

import lombok.Builder;

@Builder
public record UserDto(
        String email,
        String password
) {
}
