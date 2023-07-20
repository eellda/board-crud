package com.exam.boardtest.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AutoCloseable.class)
@Getter
public class Time {

  @CreationTimestamp // create time
  @Column(updatable = false) // modify X
  private LocalDateTime createDate;

  @UpdateTimestamp // update time
  @Column(insertable = false) // insert X
  private LocalDateTime updatedDate;
}
