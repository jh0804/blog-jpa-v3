package shop.mtcoding.blog;

import lombok.Getter;

/*
    리스트 = [1, 2, 3] 가변
    튜플 = (1, 2, 3) 불변
 */
// @Getter // value가 private 이면 getter가 필요 -> 그냥 public으로 만들면 됨
public enum ApplyEnum { // 열거형
    PASS("합격"), FAIL("불합격");

    public String value;

    ApplyEnum(String value) {
        this.value = value;
    }
}
