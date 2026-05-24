package org.example.irctc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class AuthResponse {
        @Getter @Setter
        private String accessToken;
        @Getter @Setter
        private String refreshToken;
        @Getter @Setter
        private String message;


}
