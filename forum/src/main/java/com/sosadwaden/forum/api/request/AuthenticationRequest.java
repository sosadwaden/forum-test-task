package com.sosadwaden.forum.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotNull(message = "Nickname cannot be empty")
    @Size(min = 3, max = 50, message = "The nickname must be between 3 and 50 characters long")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
}
