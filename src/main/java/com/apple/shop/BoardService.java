package com.apple.shop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final BoardRepository boardRepository;

    public void saveBoard(String title, String content) {
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        board.setDate(LocalDateTime.now().format(formatter));

        entityManager.persist(board);
    }

    public List<BoardDTO> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(board -> new BoardDTO(board.getId(), board.getTitle(), board.getContent(), board.getDate()))
                .collect(Collectors.toList());
    }

    public BoardDTO getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
        return new BoardDTO(board.getId(), board.getTitle(), board.getContent(), board.getDate());
    }

    public void updateBoard(Long id, String title, String content) {
        Board board = entityManager.find(Board.class, id);
        if (board == null) {
            throw new IllegalArgumentException("Invalid board ID");
        }
        board.setTitle(title);
        board.setContent(content);
    }

    public void deleteBoard(Long id) {
        Board board = entityManager.find(Board.class, id);
        if (board != null) {
            entityManager.remove(board);
        }
    }
}
