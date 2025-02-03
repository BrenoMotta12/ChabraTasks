package com.bm12.chabra.repository;


import com.bm12.chabra.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRespository extends JpaRepository<Task, UUID> {

    @Query("SELECT DISTINCT t FROM task t " +
            "JOIN t.responsibles r " +
            "WHERE r.id = :userId AND t.listTask.id = :listId")
    List<Task> findTaskByListIdAndResponsible(@Param("listId") UUID listId, @Param("userId") UUID userId);

    @Query("SELECT DISTINCT t FROM task t " +
            "WHERE t.listTask.id = :listId")
    List<Task> findTaskByListId(@Param("listId") UUID listId);
}
