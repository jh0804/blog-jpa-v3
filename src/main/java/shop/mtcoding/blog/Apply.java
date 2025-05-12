package shop.mtcoding.blog;

import lombok.Data;
import lombok.Getter;

// jpa와 상관없는 enum 공부를 위한 클래스
@Getter
public class Apply { // 지원
    private Integer id; // 지원번호
    private String name; // 지원자명
    private Integer comId; // 회사아이디
    private String status; // 1. 합격 2.불합격 (도메인(범주)를 설정) -> db에는 ApplyStatus, Enum 타입이 없으므로 String이 맞다!

    // ApplyEnum : 넣을 때 제약을 주면 된다! -> 범주가 세팅이 됨
    public Apply(Integer id, String name, Integer comId, ApplyEnum status) { // (ApplyStatus status) 안됨 -> Interface의 한계
        this.id = id;
        this.name = name;
        this.comId = comId;
        this.status = status.value;
    }
}
