package com.pedsouto.taskservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pedsouto.taskservice.infra.enums.NotificationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskDto(
        String id,
        String title,
        String description,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime eventDate,

        String userEmail,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime updatedAt,

        NotificationStatus notificationStatus
) {
}
