package com.exam.boardtest.service;

import com.exam.boardtest.dto.BoardDTO;
import com.exam.boardtest.entity.Board;
import com.exam.boardtest.entity.BoardFile;
import com.exam.boardtest.repository.BoardFileRepository;
import com.exam.boardtest.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final BoardFileRepository boardFileRepository;

  @Transactional
  public void save(BoardDTO boardDTO) throws IOException {
    // dto -> entity
    if (boardDTO.getFile().isEmpty()) {
      Board board = Board.toSaveEntity(boardDTO);
      boardRepository.save(board);
    } else {
      MultipartFile file = boardDTO.getFile(); // 꺼냄
      String originalFileName = file.getOriginalFilename(); // 파일 이름 가져옴
      String storedFileName = UUID.randomUUID() + "_" + originalFileName;
      String savePath = System.getProperty("user.dir") + "/src/main/resources/static/files/" + storedFileName;
      file.transferTo(new File(savePath));
      Board board = Board.toSaveFileEntity(boardDTO);

      int saveId = boardRepository.save(board).getId();
      Board saveBoard = boardRepository.findById(saveId).get();
      BoardFile boardFile = BoardFile.toBoardFile(saveBoard, originalFileName, storedFileName);
      boardFileRepository.save(boardFile);
    }

  }

  @Transactional
  public List<BoardDTO> findAll() {
    // entity -> dto
    List<Board> boardList = boardRepository.findAll();
    List<BoardDTO> boardDTOList = new ArrayList<>();

    for (Board board : boardList) {
      boardDTOList.add(BoardDTO.toBoardDTO(board));
    }

    return boardDTOList;
  }

  @Transactional
  public void countHits(int id) {
    boardRepository.countHits(id);
  }

  @Transactional
  public BoardDTO findById(int id) {
    Optional<Board> optionalBoard = boardRepository.findById(id);

    if (optionalBoard.isPresent()) {
      Board board = optionalBoard.get();
      BoardDTO boardDTO = BoardDTO.toBoardDTO(board);
      return boardDTO;
    } else {
      return null;
    }
  }

  public BoardDTO update(BoardDTO boardDTO) {
    Board board = Board.toUpdate(boardDTO);
    boardRepository.save(board);
    return findById(boardDTO.getId());
  }

  public void delete(int id) {
    boardRepository.deleteById(id);
  }

  public Page<BoardDTO> paging(Pageable pageable) {
    int page = pageable.getPageNumber() - 1;
    int pageLimit = 3;
    Page<Board> boards =
            boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

    System.out.println("content: " + boards.getContent());
    System.out.println("totalElements: " + boards.getTotalElements()); // 전체 글 갯수
    System.out.println("number: " + boards.getNumber());               // db 요청 페이지 번호
    System.out.println("totalPages: " + boards.getTotalPages());       // 전체 페이지
    System.out.println("size: " + boards.getSize());                   // 한 페이지의 글 갯수
    System.out.println("previous: " + boards.hasPrevious());           // 이전 페이지
    System.out.println("first: " + boards.isFirst());
    System.out.println("last: " + boards.isLast());

    // entity -> dto
    Page<BoardDTO> boardDTOS = boards.map(board -> new BoardDTO(board.getId(), board.getWriter(), board.getTitle(), board.getHits(), board.getCreateDate()));
    return boardDTOS;
  }
}
