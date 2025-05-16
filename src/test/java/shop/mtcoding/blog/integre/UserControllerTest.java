package shop.mtcoding.blog.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.MyRestDoc;
import shop.mtcoding.blog._core.util.JwtUtil;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserRequest;

// 컨트롤러 통합 테스트
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest extends MyRestDoc {

    @Autowired // di 코드
    private ObjectMapper om; // java 객체 <-> json

    private String accessToken;

    @BeforeEach
    public void setUp(){
        // 테스트 시작 전에 실행할 코드
        //System.out.println("setUp");

        User ssar = User.builder().id(1).username("ssar").build();
        accessToken = JwtUtil.create(ssar);
    }

    @AfterEach
    public void tearDown(){
        // 테스트 후 정리할 코드
        //System.out.println("tearDown");
    }

    @Test
    public void join_username_uk_fail_test() throws Exception { // 이 메서드를 호출한 주체에게 예외 위임 -> 지금은 jvm 이다
        // given -> 가짜 데이터
        UserRequest.JoinDTO reqDTO = new UserRequest.JoinDTO();
        reqDTO.setEmail("ssar@nate.com");
        reqDTO.setPassword("1234");
        reqDTO.setUsername("ssar");

        String requestBody = om.writeValueAsString(reqDTO);
//        System.out.println(requestBody); // {"username":"haha","password":"1234","email":"haha@nate.com"}

        // when -> 테스트 실행
        ResultActions actions = mvc.perform( // 주소가 틀리면 터지고, json 아닌거 넣으면 터지고, 타입이 달라도 터지고. 따라서 미리 터진다고 알려줌
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye -> 결과 눈으로 검증
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        //System.out.println(responseBody); // {"status":200,"msg":"성공","body":{"id":4,"username":"haha","email":"haha@nate.com","createdAt":"2025-05-13 11:45:23.604577"}}

        // then -> 결과를 코드로 검증 // json의 최상위 객체를 $ 표기한다
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("중복된 유저네임이 존재합니다."));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body").value(Matchers.nullValue()));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document); // 응답되는 결과를 interceptor해서 console에 찍음 -> 이걸 redirection해서 파일로 (io redirection)
    }

    @Test
    public void check_username_available_test() throws Exception {
        // given
        String username = "haha";

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/check-username-available/{username}", username)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        //System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.available").value(true));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void update_test() throws Exception {
        // given
        UserRequest.UpdateDTO reqDTO = new UserRequest.UpdateDTO();
        reqDTO.setPassword("12345");
        reqDTO.setEmail("ssar@naver.com");

        String requestBody = om.writeValueAsString(reqDTO);
        // System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .put("/s/api/user")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("ssar"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value("ssar@naver.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt")
                .value(Matchers.matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d+$")));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO reqDTO = new UserRequest.LoginDTO();
        reqDTO.setUsername("ssar");
        reqDTO.setPassword("1234");

        String requestBody = om.writeValueAsString(reqDTO);
        // System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        // System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.accessToken").isNotEmpty());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.refreshToken").isNotEmpty());
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_test() throws Exception {
        // given (가짜데이터)
        UserRequest.JoinDTO reqDTO = new UserRequest.JoinDTO();
        reqDTO.setUsername("haha");
        reqDTO.setPassword("1234");
        reqDTO.setEmail("haha@nate.com"); // java 객체 상태 -> json으로 만들어야 됨

        String requestBody = om.writeValueAsString(reqDTO); // 엄마가 알려주는 예외(CHECKED EXCEPTION)
//        System.out.println(requestBody);

        // when (테스트 실행)
        ResultActions actions = mvc.perform( // perform : api 때려주는 것 -> header+body를 돌려준다.(=actions)
                // post 때리고 json 보내고 그러면 헤더에 mime type, body에 뭘 넣어야 하는지 알아야
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON) // 문자열 넣으면 실수 할 수 있음 -> media type을 넣는게 낫다.
        );

        // eye (결과 눈으로 검증)
        String responseBody = actions.andReturn().getResponse().getContentAsString(); // getModelAndView : MVC 패턴의 request.setAttribute("model", respDTO); 이런거 검증 가능
        // System.out.println(responseBody);

        // then (결과 코드로 검증) -> 이 코드가 나중에 테스트 서버에서 돌거고 테스트가 안되면 빨간색이 터져야 되므로
        // 1. java 객체로 변경해서 검증 -> 귀찮
        // 2. json 데이터 직접 검증 기능 제공
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(4));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("haha"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value("haha@nate.com"));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
