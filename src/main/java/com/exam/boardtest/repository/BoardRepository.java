package com.exam.boardtest.repository;

import com.exam.boardtest.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
  @Modifying
  @Query(value = "UPDATE Board b SET b.hits = b.hits + 1 WHERE b.id = :id")
  void countHits(@Param("id") int id);
}
