package shop.mtcoding.blog.temp;

import org.junit.jupiter.api.Test;

import java.util.Optional;

/*
Optional.of(value)	절대 null이 아니어야 함
Optional.ofNullable(value)	null일 수도 있음
Optional.empty()	빈 Optional 생성

get()	값 꺼냄 (값이 없으면 예외 발생)
orElse(default)	값이 없으면 기본값 리턴
orElseGet(Supplier)	값이 없으면 람다 결과 리턴
orElseThrow()	값이 없으면 예외 발생

isPresent()	값이 있는지 확인
ifPresent(Consumer)	값이 있으면 처리
filter(Predicate)	조건에 맞는 값만 유지
 */
public class OptionalTest {
    @Test
    public void t1(){
        String name = "metacoding";
        Optional<String> opt = Optional.ofNullable(name);

        if(opt.isPresent()){
            System.out.println(opt.get());
        }else{
            System.out.println("선물박스에 값이 없어요.");
        }
    }

    @Test
    public void t2(){
        String name = null;
        Optional<String> opt = Optional.ofNullable(name);

        String result = opt.orElseThrow(() -> new RuntimeException("값이 없어요."));
        System.out.println(result);
    }

    @Test
    public void t3(){
        String name = null;
        Optional<String> opt = Optional.ofNullable(name);

        String result = opt.orElseGet(() -> "metacoding");
        System.out.println(result);
    }
}