package com.bm12.chabra.repository;


import com.bm12.chabra.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRespository extends JpaRepository<Task, UUID> {
}
