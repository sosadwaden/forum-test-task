package com.sosadwaden.forum.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicPOSTRequest {

    @NotNull(message = "The topic name cannot be empty")
    @Size(min = 3, max = 50, message = "The topic name should be between 3 and 50 characters long")
    String topicName;
}
