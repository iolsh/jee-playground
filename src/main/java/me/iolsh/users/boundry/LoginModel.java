package me.iolsh.users.boundry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Schema(description = "Login data")
public class LoginModel {
    @NotNull
    @Schema(required = true, example = "John123")
    private String userName;
    @NotNull
    @Schema(required = true, example = "passw0rd")
    private String password;
}
