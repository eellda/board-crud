package com.exam.boardtest.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_file")
public class BoardFile extends Time {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private String originalFileName;

  @Column
  private String storedFileName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  public static BoardFile toBoardFile(Board board, String originalFileName, String storedFileName) {
    BoardFile boardFile = new BoardFile();
    boardFile.setOriginalFileName(originalFileName);
    boardFile.setStoredFileName(storedFileName);
    boardFile.setBoard(board);

    return boardFile;
  }
}
