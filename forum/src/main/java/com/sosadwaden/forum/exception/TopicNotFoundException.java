package com.sosadwaden.forum.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicNotFoundException extends RuntimeException {
    final String message;
}
