package com.apple.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private String date;

    public BoardDTO(Long id, String title, String content, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
