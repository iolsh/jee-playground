package me.iolsh;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
@OpenAPIDefinition(info = @Info(version = "0.1", title = "Playground application API",
        description = "Playground application is a sandbox to evaluate JEE related concepts. " +
                "This API provides resources helpful for hacking the code ;). " +
                "Register, login, and play with other secured stuff.",
        license = @License(name = "MIT", url = "https://en.wikipedia.org/wiki/MIT_License"),
        contact = @Contact(name = "Jacek Olszewski", email = "jacek.olszewski@gmail.com")),
        components = @Components(
                securitySchemes = {
                        @SecurityScheme(
                                securitySchemeName = "JWT",
                                scheme = "bearer",
                                bearerFormat = "JWT",
                                type = SecuritySchemeType.HTTP,
                                description = "authentication needed to obtain restricted resources",
                                in = SecuritySchemeIn.HEADER
                        )
                }
        )
)
public class ApplicationServices extends Application {

}
