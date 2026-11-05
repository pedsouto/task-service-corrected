package com.pedsouto.taskservice.infra.entity;

import com.pedsouto.taskservice.infra.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tasks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    private String id;
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String userEmail;
    private NotificationStatus notificationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
