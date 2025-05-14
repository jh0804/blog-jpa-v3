package shop.mtcoding.blog.temp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import shop.mtcoding.blog.user.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TokenTest {

    @Test
    public void create_test() {
        User user = User.builder()
                .id(1)
                .username("ssar")
                .password("$2a$10$dd85C7oDcouLxnR1jYVXVOWbGdfzYOw9Q.pHvpOz0j4we7LEs8i.u")
                .email("ssar@nate.com")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        String jwt = JWT.create()
                .withSubject("blogv3")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC256("metacoding"));

        // 198 156 236 87 42 53 186 254 56 151 169 7 107 178 5 197 147 172 56 100 145 97 133 14 17 46 135 193 73 199 201 144
        // xpzsVyo1uv44l6kHa7IFxZOsOGSRYYUOES6HwUnHyZA
        System.out.println(jwt);
    }

    @Test
    public void verify_test() {
        User user = User.builder()
                .id(1)
                .username("ssar")
                .password("$2a$10$dd85C7oDcouLxnR1jYVXVOWbGdfzYOw9Q.pHvpOz0j4we7LEs8i.u")
                .email("ssar@nate.com")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        String jwt = JWT.create()
                .withSubject("blogv3")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC256("metacoding"));

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("metacoding")).build().verify(jwt);
        Integer id = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getClaim("username").asString();
        System.out.println(id);
        System.out.println(username);
    }
}
