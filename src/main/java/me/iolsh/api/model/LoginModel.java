package me.iolsh.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LoginModel {
    @NotNull
    private String userName;
    @NotNull
    private String password;
}
