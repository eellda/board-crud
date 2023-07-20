package com.exam.boardtest.repository;

import com.exam.boardtest.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFile, Integer> {
}
