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
public class MessagePOSTRequest {

    @NotNull(message = "Nickname cannot be empty")
    @Size(min = 3, max = 50, message = "The nickname must be between 3 and 50 characters long")
    String nickname;

    @NotNull(message = "The message must contain at least 1 character")
    @Size(min = 1, max = 2000, message = "The message must contain at least 1 character and no more than 2000 characters")
    String text;
}
