package shop.mtcoding.blog.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.util.JwtUtil;
import shop.mtcoding.blog.reply.ReplyRequest;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserRequest;

@Transactional
@AutoConfigureMockMvc // MockMvc 클래스가 IoC로드
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ReplyControllerTest {

    @Autowired // di 코드
    private ObjectMapper om; // java 객체 <-> json

    @Autowired
    private MockMvc mvc; // 가짜 환경을 때리는 클래스

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
    public void save_test() throws Exception {
        // given
        ReplyRequest.SaveDTO reqDTO = new ReplyRequest.SaveDTO();
        reqDTO.setBoardId(1);
        reqDTO.setContent("댓글작성");

        String requestBody = om.writeValueAsString(reqDTO);
        // System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/s/api/reply")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        // System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(6));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("댓글작성"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.userId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boardId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt")
                .value(Matchers.matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d+$")));
    }

    @Test
    public void delete_test() throws Exception {
        // given
        Integer replyId = 1;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/s/api/reply/{id}", replyId)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        // System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body").value(Matchers.nullValue()));
    }
}
