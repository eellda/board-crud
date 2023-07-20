package com.exam.boardtest.dto;

import com.exam.boardtest.entity.Board;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
  private int id;
  private String writer;
  private String password;
  private String title;
  private String content;
  private int hits;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  private MultipartFile file;
  private String originalFileName;
  private String storedFileName;
  private int fileAttached; // 파일 첨부 여부

  public BoardDTO(int id, String writer, String title, int hits, LocalDateTime createdDate) {
    this.id = id;
    this.writer = writer;
    this.title = title;
    this.hits = hits;
    this.createdDate = createdDate;
  }

  public static BoardDTO toBoardDTO(Board board) {
    BoardDTO boardDTO = new BoardDTO();
    boardDTO.setId(board.getId());
    boardDTO.setWriter(board.getWriter());
    boardDTO.setPassword(board.getPassword());
    boardDTO.setTitle(board.getTitle());
    boardDTO.setContent(board.getContent());
    boardDTO.setHits(board.getHits());
    boardDTO.setCreatedDate(board.getCreateDate());
    boardDTO.setUpdatedDate(board.getUpdatedDate());

    if (board.getFileAttached() == 0) {
      boardDTO.setFileAttached(board.getFileAttached());
    } else {
      boardDTO.setFileAttached(board.getFileAttached());
      // SELECT * FROM board b, board_file bf WHERE b.id = bf.board_id AND WHERE b.id=?
      boardDTO.setOriginalFileName(board.getBoardFileList().get(0).getOriginalFileName());
      boardDTO.setStoredFileName(board.getBoardFileList().get(0).getStoredFileName());
    }

    return boardDTO;
  }
}
