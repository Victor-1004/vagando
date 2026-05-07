package com.vic.vagando.infrastructure.auth.dto;

import com.vic.vagando.app.domain.user.UserRole;

public class RegisterDTO {
    private String email;
    private String password;
    private UserRole role;

    public RegisterDTO() {
    }


    public RegisterDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public UserRole getRole() {
        return role;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }


}
