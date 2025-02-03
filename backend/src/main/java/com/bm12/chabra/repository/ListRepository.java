package com.bm12.chabra.repository;

import com.bm12.chabra.model.ListTask;
import com.bm12.chabra.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ListRepository extends JpaRepository<ListTask, UUID>{

    @Query("SELECT DISTINCT l FROM list_tasks l " +
            "JOIN l.tasks t " +
            "JOIN t.responsibles r " +
            "WHERE r.id = :userId")
    List<ListTask> findListByUserResponsible(@Param("userId") UUID userId);
}
