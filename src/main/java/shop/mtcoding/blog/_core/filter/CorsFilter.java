package shop.mtcoding.blog._core.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");
        log.debug("Origin : "+origin);

        // 전부 JS 요청(fetch ajax)에 대한 것
        //response.setHeader("Access-Control-Allow-Origin", origin); // => origin 다 허용 (우리 서버는 이렇게 만들기)
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500"); // => origin 다 허용 (정확하게 적어야 됨 localhost != 127.0.0.1)
        response.setHeader("Access-Control-Expose-Headers", "Authorization"); // JS로 Authorization에 JWT 넣을 수도 있음 -> 허용 (이 헤더 응답을 JS로 접근하게 허용할지 -> 지금 서버에서는 불필요)
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS"); // OPTIONS : 그 다음 요청 때문에 허용 -> 여기서는 preflight와 상관 없음
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Key, Content-Type, Accept, Authorization"); // 클라이언트가 서버로 보내는 헤더 req 요청할 때 이 헤더 허용해줄게
        // 헤더 앞 X-는 프로토콜이 아닌 우리가 임의로 만드는 헤더 : e.g X-key
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 쿠키의 세션값 허용

        // Preflight 요청을 허용하고 바로 응답하는 코드
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK); // HttpServletResponse.SC_OK = 200
            // 여기서 통신 끝. 위 헤더 전달
        }else {
            chain.doFilter(req, res);
        }
    }
}