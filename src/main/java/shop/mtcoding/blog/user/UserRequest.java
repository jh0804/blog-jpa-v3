package shop.mtcoding.blog.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserRequest {

    @Data
    public static class UpdateDTO {

        @Schema(description = "비밀번호 (4~20자)", example = "1234")
        @Size(min = 4, max = 20)
        private String password;

        @Schema(description = "이메일 주소", example = "user@example.com")
        @Pattern(regexp = "^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 적어주세요")
        private String email;
    }

    @Data
    public static class JoinDTO {

        @Schema(description = "유저네임 (2~20자, 특수문자/한글 불가)", example = "metacoding")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "유저네임은 2-20자이며, 특수문자,한글이 포함될 수 없습니다")
        private String username;

        @Schema(description = "비밀번호 (4~20자)", example = "1234")
        @Size(min = 4, max = 20)
        private String password;

        @Schema(description = "이메일 주소", example = "user@example.com")
        @Pattern(regexp = "^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 적어주세요")
        private String email;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }
    }

    @Data
    public static class LoginDTO {

        @Schema(description = "유저네임 (2~20자)", example = "metacoding")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "유저네임은 2-20자이며, 특수문자,한글이 포함될 수 없습니다")
        private String username;

        @Schema(description = "비밀번호 (4~20자)", example = "1234")
        @Size(min = 4, max = 20)
        private String password;

        @Schema(description = "자동 로그인 여부 (체크시 'on')", example = "on", nullable = true)
        private String rememberMe; // check되면 on, 안되면 null
    }
}