package shop.mtcoding.blog.user;

import lombok.Data;

import java.sql.Timestamp;

public class UserResponse {

    // 기본이 되는 model과 똑같이 생긴 DTO
    // pw와 같은 보안에 민감한 데이터는 담지X
    // RestAPI 규칙2 : DTO에 민감정보 빼기, 날짜는 String으로 (날짜 공부하기 전까지)
    @Data
    public static class DTO{
        private Integer id;
        private String username;
        private String email;
        private String createdAt;

        public DTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.createdAt = user.getCreatedAt().toString();
        }
    }
}
