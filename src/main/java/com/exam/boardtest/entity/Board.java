package com.exam.boardtest.entity;

import com.exam.boardtest.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board") // table create
public class Board extends Time {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
  private int id;

  @Column(length = 30, nullable = false)
  private String writer;

  @Column(length = 50)
  private String password;

  @Column(nullable = false)
  private String title;

  @Column(length = 500, nullable = false)
  private String content;

  @Column
  private int hits;

  @Column
  private int fileAttached;

  @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<BoardFile> boardFileList = new ArrayList<>();

  public static Board toSaveEntity(BoardDTO boardDTO) {
    Board board = new Board();
    board.setWriter(boardDTO.getWriter());
    board.setPassword(boardDTO.getPassword());
    board.setHits(0);
    board.setTitle(boardDTO.getTitle());
    board.setContent(boardDTO.getContent());
    board.setFileAttached(0);

    return board;
  }

  public static Board toUpdate(BoardDTO boardDTO) {
    Board board = new Board();
    board.setId(boardDTO.getId()); // need
    board.setWriter(boardDTO.getWriter());
    board.setPassword(boardDTO.getPassword());
    board.setHits(boardDTO.getHits());
    board.setTitle(boardDTO.getTitle());
    board.setContent(boardDTO.getContent());
    board.setFileAttached(boardDTO.getFileAttached());

    return board;
  }

  public static Board toSaveFileEntity(BoardDTO boardDTO) {
    Board board = new Board();
    board.setWriter(boardDTO.getWriter());
    board.setPassword(boardDTO.getPassword());
    board.setHits(0);
    board.setTitle(boardDTO.getTitle());
    board.setContent(boardDTO.getContent());
    board.setFileAttached(1);

    return board;
  }
}
