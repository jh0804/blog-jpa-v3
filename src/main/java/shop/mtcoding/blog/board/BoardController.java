package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.util.Resp;
import shop.mtcoding.blog.user.User;

@CrossOrigin
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final HttpSession session;

    @PutMapping("/board/{id}")
    public @ResponseBody Resp<?> update(@PathVariable("id") Integer id, @Valid @RequestBody BoardRequest.UpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DTO respDTO = boardService.글수정하기(reqDTO, id, sessionUser.getId());
        return Resp.ok(respDTO);
    }

    @GetMapping("/board/{id}")
    public @ResponseBody Resp<?> getBoardOne(@PathVariable("id") int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DTO respDTO = boardService.글보기(id, sessionUser.getId());
        return Resp.ok(respDTO);
    }

    @GetMapping("/board/{id}/detail")
    public @ResponseBody Resp<?> getBoardDetail(@PathVariable("id") Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer sessionUserId = (sessionUser == null ? null : sessionUser.getId());
        BoardResponse.DetailDTO detailDTO = boardService.글상세보기(id, sessionUserId);
        return Resp.ok(detailDTO);
    }

    // localhost:8080?page=0
    // localhost:8080
    @GetMapping("/")
    public @ResponseBody Resp<?> list(@RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(required = false, value = "keyword", defaultValue = "") String keyword) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        BoardResponse.ListDTO respDTO;
        if (sessionUser == null) {
            respDTO = boardService.글목록보기(null, page, keyword);
        } else {
            respDTO = boardService.글목록보기(sessionUser.getId(), page, keyword);
        }

        return Resp.ok(respDTO);
    }

    @PostMapping("/board")
    public @ResponseBody Resp<?> save(@Valid @RequestBody BoardRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DTO respDTO = boardService.글쓰기(reqDTO, sessionUser);
        return Resp.ok(respDTO);
    }
}
