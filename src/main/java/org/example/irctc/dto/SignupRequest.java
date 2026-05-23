package org.example.irctc.dto;

import lombok.Getter;
import lombok.Setter;

public class SignupRequest {
        @Getter @Setter
        private String name;
        @Getter @Setter
        private String email;
        @Getter @Setter
        private String password;

}
