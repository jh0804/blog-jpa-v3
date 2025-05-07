package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.error.ex.Exception400;
import shop.mtcoding.blog._core.error.ex.Exception401;
import shop.mtcoding.blog._core.error.ex.Exception404;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// 비지니스로직, 트랜잭션처리, DTO 완료
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;


    // RestAPI 규칙1 : insert 요청시에 그 행을 dto에 담아서 리턴한다.
    @Transactional
    public UserResponse.DTO 회원가입(UserRequest.JoinDTO reqDTO) {
        try {
            User userPS = userRepository.save(reqDTO.toEntity());
            return new UserResponse.DTO(userPS);
        } catch (Exception e) {
            throw new Exception400("잘못된 요청입니다");
        }
    }

    // TODO : A4용지에다가 id, username 적어, A4용지를 서명, A4용지를 돌려주기
    public User 로그인(UserRequest.LoginDTO loginDTO) {
        User userPS = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new Exception401("유저네임 혹은 비밀번호가 틀렸습니다"));

        if (!userPS.getPassword().equals(loginDTO.getPassword())) {
            throw new Exception401("유저네임 혹은 비밀번호가 틀렸습니다");
        }
        return userPS;
    }

    public Map<String, Object> 유저네임중복체크(String username) {
        Optional<User> userOP = userRepository.findByUsername(username);
        Map<String, Object> dto = new HashMap<>();

        if (userOP.isPresent()) { // 부정으로 물어보는 것보다 긍정으로 물어보는 것이 낫다!
            dto.put("available", false);
        } else {
            dto.put("available", true);
        }
        return dto;
    }

    // TODO : RestAPI 규칙3 : update된 데이터도 돌려줘야 함
    @Transactional
    public User 회원정보수정(UserRequest.UpdateDTO updateDTO, Integer userId) {

        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new Exception404("자원을 찾을 수 없습니다"));

        userPS.update(updateDTO.getPassword(), updateDTO.getEmail()); // 영속화된 객체의 상태변경
        return userPS; // 리턴한 이유는 세션을 동기화해야해서!!
    } // 더티체킹 -> 상태가 변경되면 update을 날려요!!
}
