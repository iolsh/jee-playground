package me.iolsh.infrastructure.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.iolsh.users.entity.User;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class Security {

    public static final String JWT_SECRET = "519b28d5-05ca-40f2-a32a-292fa9952d70";
    public static final Duration TOKEN_VALIDITY = Duration.ofMinutes(10);

    public String createToken(User user, Duration validity, UriInfo uriInfo) {

        LocalDateTime expTime = LocalDateTime.now().plus(validity);
        Date expiration = Date.from(expTime.atZone(ZoneId.systemDefault()).toInstant());

        Key key = generateKey(JWT_SECRET);

        return Jwts.builder().setSubject(user.getEmail()).setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date()).setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, key).setAudience(uriInfo.getBaseUri().toString())
                .compact();
    }

    public Key generateKey(String keyString) {
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
    }

    public String hash(String text) {
        return BCrypt.withDefaults().hashToString(12, text.toCharArray());
    }

    public boolean verifyPassword(String candidate, String hashed) {
        BCrypt.Result result = BCrypt.verifyer().verify(candidate.toCharArray(), hashed);
        return result.verified;
    }

}
