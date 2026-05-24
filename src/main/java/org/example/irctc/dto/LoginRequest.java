package org.example.irctc.dto;
import lombok.Getter;
import lombok.Setter;
public class LoginRequest {
    @Getter @Setter
    public String name;
    @Getter @Setter
    public String password;

}
