package com.SpringSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

    private long userId;
    private String name;
    private String email;
    private String password;

}
