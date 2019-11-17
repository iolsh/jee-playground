package me.iolsh.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserModel {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
