package shop.mtcoding.blog.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.mtcoding.blog._core.util.JwtUtil;
import shop.mtcoding.blog.board.BoardRequest;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserRequest;

// 배열은 0번지만 상태검사하면 됨
@AutoConfigureMockMvc // MockMvc 클래스가 IoC로드
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BoardControllerTest {

    @Autowired // di 코드
    private ObjectMapper om; // java 객체 <-> json

    @Autowired
    private MockMvc mvc; // 가짜 환경을 때리는 클래스

    private String accessToken;

    // save_test 시작 전
    @BeforeEach // 함수 시작 전마다 실행됨 -> 함수 100개면 100번 실행된다. @BeforeAll도 있는데 이건 static으로 만들어야 해서 어쩌구 암튼 하지마세용
    public void setUp(){
        // 테스트 시작 전에 실행할 코드
        System.out.println("setUp");

        User ssar = User.builder().id(1).username("ssar").build();
        accessToken = JwtUtil.create(ssar);
    }

    // save_test 끝나고
    @AfterEach
    public void tearDown(){
        // 테스트 후 정리할 코드
        System.out.println("tearDown");
    }

    @Test
    public void save_test() throws Exception {
        // given
        BoardRequest.SaveDTO reqDTO = new BoardRequest.SaveDTO();
        reqDTO.setTitle("제목21");
        reqDTO.setContent("내용21");
        reqDTO.setIsPublic(true);

        String requestBody = om.writeValueAsString(reqDTO);
        // System.out.println(requestBody);

        // when (테스트 실행)
        // 토큰 여기서 만들어야 된다.
//        User ssar = User.builder().id(1).username("ssar").build();
//        String accessToken = JwtUtil.create(ssar); // 여기 User 객체 들어가야 됨 -> 위에서 만든다.
        // accessToken을 헤더에 전달하면 된다.

        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/s/api/board")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye (결과 눈으로 검증)
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then (결과 코드로 검증) -> 이 코드가 나중에 테스트 서버에서 돌거고 테스트가 안되면 빨간색이 터져야 되므로
        // 1. java 객체로 변경해서 검증 -> 귀찮
        // 2. json 데이터 직접 검증 기능 제공
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(21));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("제목21"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("내용21"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isPublic").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.userId").value(1));
    }
}
