package com.pedsouto.taskservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pedsouto.taskservice.infra.enums.NotificationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskDto(
        String id,
        @NotBlank(message = "O título é obrigatório")
        @Size(max = 120, message = "O título deve ter no máximo 120 caracteres")
        String title,
        @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres")
        String description,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @NotNull(message = "A data do evento é obrigatória")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime eventDate,

        String userEmail,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime updatedAt,

        NotificationStatus notificationStatus
) {
}