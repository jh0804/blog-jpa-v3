package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.util.Resp;

import java.util.Map;

@RequiredArgsConstructor
@RestController // json만 리턴!!
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @PutMapping("/s/api/user")
    public String update(@Valid @RequestBody UserRequest.UpdateDTO updateDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // TODO: JWT 이후에
        User userPS = userService.회원정보수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", userPS);
        return "redirect:/";
    }

    @GetMapping("/api/check-username-available/{username}")
    public ResponseEntity<?> checkUsernameAvailable(@PathVariable("username") String username) {
        Map<String, Object> dto = userService.유저네임중복체크(username);
        return Resp.ok(dto);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO reqDTO, Errors errors) {
        UserResponse.DTO respDTO = userService.회원가입(reqDTO);
        return Resp.ok(respDTO);
    }

    // TODO: JWT 이후에
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO, Errors errors, HttpServletResponse response) {
        UserResponse.TokenDTO respDTO = userService.로그인(loginDTO);
        return Resp.ok(respDTO);
    }

    // AccessToken만으로는 Logout 을 할 수 없다.

}