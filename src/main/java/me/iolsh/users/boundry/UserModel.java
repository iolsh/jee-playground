package me.iolsh.users.boundry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Valid
@Schema(description = "New user data")
public class UserModel {
    @NotNull
    @Schema(required = true, example = "John123")
    private String userName;
    @NotNull
    @Schema(required = true, example = "passw0rd")
    private String password;
    @NotNull
    @Schema(required = true, example = "John")
    private String firstName;
    @NotNull
    @Schema(required = true, example = "Deep")
    private String lastName;
    @Email
    @Schema(required = true, example = "john.deep@fake.com")
    private String email;
}
