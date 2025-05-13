package shop.mtcoding.blog.integre;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import shop.mtcoding.blog.user.UserRequest;

@AutoConfigureMockMvc // MockMvc 클래스가 IoC로드
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest {

    @Autowired // di 코드
    private ObjectMapper om; // java 객체 <-> json

    @Autowired
    private MockMvc mvc; // 가짜 환경을 때리는 클래스

    // 나머지 메서드도 전부 테스트 

    @Test
    public void login_test() throws Exception {
        // given

        // when

        // eye

        // then
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
                MockMvcRequestBuilders.post("/join")
                        .content(requestBody).contentType(MediaType.APPLICATION_JSON) // 문자열 넣으면 실수 할 수 있음 -> media type을 넣는게 낫다.
        );

        // eye (결과 눈으로 검증)
        String responseBody = actions.andReturn().getResponse().getContentAsString(); // getModelAndView : MVC 패턴의 request.setAttribute("model", respDTO); 이런거 검증 가능
        System.out.println(responseBody);

        // then (결과 코드로 검증) -> 이 코드가 나중에 테스트 서버에서 돌거고 테스트가 안되면 빨간색이 터져야 되므로
        // 1. java 객체로 변경해서 검증 -> 귀찮
        // 2. json 데이터 직접 검증 기능 제공
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(5));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("haha"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value("haha@nate.com"));
    }
}
