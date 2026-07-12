package com.lesson.memo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lesson.memo.model.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByTitleContaining(String keyword);
    List<Memo> findByContentContaining(String keyword);
    List<Memo> findByTitleContainingOrContentContaining(String keyword1, String keyword2);
}