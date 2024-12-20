package com.apple.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String board(@CookieValue(value = "userId", required = false) String userId, Model model) {
        model.addAttribute("boards", boardService.getAllBoards());
        model.addAttribute("isLoggedIn", userId != null);
        return "notice.html";
    }


    @GetMapping("/board/create")
    public String createBoard() {
        return "createBoard.html";
    }

    @PostMapping("/board/save")
    public String saveBoard(@RequestParam String title, @RequestParam String content) {
        boardService.saveBoard(title, content);
        return "redirect:/board";
    }

    @GetMapping("/board/edit")
    public String editBoard(@RequestParam Long id, Model model) {
        BoardDTO board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "editBoard.html";
    }

    @PostMapping("/board/update")
    public String updateBoard(@RequestParam Long id, @RequestParam String title, @RequestParam String content) {
        boardService.updateBoard(id, title, content);
        return "redirect:/board";
    }

    @PostMapping("/board/delete")
    public String deleteBoard(@RequestParam Long id) {
        boardService.deleteBoard(id);
        return "redirect:/board";
    }

    @GetMapping("/board/detail")
    public String detailBoard(@CookieValue(value = "userId", required = false) String userId,
                              @RequestParam Long id, Model model) {
        if (userId == null) {
            return "redirect:/login?message=notLoggedIn";
        }
        BoardDTO board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "detailBoard.html";
    }
}
