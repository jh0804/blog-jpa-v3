package shop.mtcoding.blog.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.util.Resp;

import java.util.Map;

// TODO : 미완
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final HttpSession session;


    @PutMapping("/user")
    public String update(@Valid @RequestBody UserRequest.UpdateDTO updateDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // TODO : JWT 이후에
        User userPS = userService.회원정보수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", userPS);
        return "redirect:/"; // 변경된 토큰을 전달
    }

    @GetMapping("/api/check-username-available/{username}")
    public @ResponseBody Resp<?> checkUsernameAvailable(@PathVariable("username") String username) {
        Map<String, Object> dto = userService.유저네임중복체크(username);
        return Resp.ok(dto);
    }

    @PostMapping("/join")
    public @ResponseBody Resp<?> join(@Valid @RequestBody UserRequest.JoinDTO reqDTO, Errors errors) {
        UserResponse.DTO respDTO = userService.회원가입(reqDTO);
        return Resp.ok(respDTO);
    }

    // TODO : JWT 이후에
    @PostMapping("/login")
    public String login(@Valid @RequestBody UserRequest.LoginDTO loginDTO, Errors errors, HttpServletResponse response) {
        //System.out.println(loginDTO);
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);

        if (loginDTO.getRememberMe() == null) {
            Cookie cookie = new Cookie("username", null);
            cookie.setMaxAge(0); // 즉시 만료
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("username", loginDTO.getUsername());
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        }

        return "redirect:/";
    }

    // TODO : JWT 이후에
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/login-form";
    }
}
