package shop.mtcoding.blog.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public class UserResponse {

    @Data
    public static class TokenDTO {
        @Schema(description = "엑세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI...")
        private String accessToken;

        @Schema(description = "리프레시 토큰", example = "dGhpc0lzUmVmcmVzaFRva2Vu")
        private String refreshToken;

        @Builder
        public TokenDTO(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    @Data
    public static class DTO {
        @Schema(description = "유저 ID", example = "1")
        private Integer id;

        @Schema(description = "유저 이름", example = "cos")
        private String username;

        @Schema(description = "이메일 주소", example = "cos@nate.com")
        private String email;

        @Schema(description = "생성일시", example = "2024-05-16T10:00:00")
        private String createdAt;

        public DTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.createdAt = user.getCreatedAt().toString();
        }
    }
}