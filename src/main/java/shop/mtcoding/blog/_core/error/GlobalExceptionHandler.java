package shop.mtcoding.blog._core.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.blog._core.error.ex.ExceptionApi400;
import shop.mtcoding.blog._core.error.ex.ExceptionApi401;
import shop.mtcoding.blog._core.error.ex.ExceptionApi403;
import shop.mtcoding.blog._core.error.ex.ExceptionApi404;
import shop.mtcoding.blog._core.util.Resp;

@RestControllerAdvice // @ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExceptionApi400.class)
    public ResponseEntity<?> exApi400(ExceptionApi400 e) {
        return Resp.fail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ExceptionApi401.class)
    public ResponseEntity<?> exApi401(ExceptionApi401 e) {
        return Resp.fail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(ExceptionApi403.class)
    public ResponseEntity<?> exApi403(ExceptionApi403 e) {
        return Resp.fail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(ExceptionApi404.class)
    public ResponseEntity<?> exApi404(ExceptionApi404 e) {
        return Resp.fail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exUnKnown(Exception e) {
        System.out.println("관리자님 보세요 : " + e.getMessage()); // 로그를 파일에 기록해서 나중에 봐야함
        return Resp.fail(HttpStatus.INTERNAL_SERVER_ERROR, "관리자에게 문의하세요");
    }
}