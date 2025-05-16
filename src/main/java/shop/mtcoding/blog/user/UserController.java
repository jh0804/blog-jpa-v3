package shop.mtcoding.blog.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.util.Resp;

import java.util.Map;

@Slf4j
@Tag(name = "User API", description = "회원가입, 로그인, 회원정보 수정 등 사용자 관련 API")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @Operation(summary = "회원정보 수정", description = "로그인한 사용자의 비밀번호와 이메일을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "회원정보 수정 성공",
            content = @Content(schema = @Schema(implementation = UserResponse.DTO.class)))
    @PutMapping("/s/api/user")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest.UpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        UserResponse.DTO respDTO = userService.회원정보수정(reqDTO, sessionUser.getId());
        return Resp.ok(respDTO);
    }

    @Operation(summary = "유저네임 중복체크", description = "해당 유저네임이 이미 사용 중인지 확인합니다.")
    @ApiResponse(responseCode = "200", description = "중복 여부 반환",
            content = @Content(schema = @Schema(implementation = Map.class)))
    @GetMapping("/api/check-username-available/{username}")
    public ResponseEntity<?> checkUsernameAvailable(
            @Parameter(description = "확인할 유저네임", example = "metacoding") @PathVariable("username") String username) {
        Map<String, Object> respDTO = userService.유저네임중복체크(username);
        return Resp.ok(respDTO);
    }

    @Operation(summary = "회원가입", description = "유저네임, 비밀번호, 이메일을 받아 회원가입을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "회원가입 성공",
            content = @Content(schema = @Schema(implementation = UserResponse.DTO.class)))
    @PostMapping("/join")
    public ResponseEntity<?> join(
            @Valid @RequestBody UserRequest.JoinDTO reqDTO,
            Errors errors,
            HttpServletResponse response,
            HttpServletRequest request) {

        log.debug(reqDTO.toString());
        log.trace("트레이스ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
        log.debug("디버그---------");
        log.info("인포ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
        log.warn("워닝ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
        log.error("에러ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");

        String hello = request.getHeader("X-Key");
        System.out.println("X-good : " + hello);

        response.setHeader("Authorization", "jooho");

        UserResponse.DTO respDTO = userService.회원가입(reqDTO);
        return Resp.ok(respDTO);
    }

    @Operation(summary = "로그인", description = "유저네임과 비밀번호를 이용하여 로그인합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = UserResponse.TokenDTO.class)))
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserRequest.LoginDTO loginDTO,
            Errors errors,
            HttpServletResponse response) {
        UserResponse.TokenDTO respDTO = userService.로그인(loginDTO);
        return Resp.ok(respDTO);
    }
}