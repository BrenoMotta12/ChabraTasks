package com.bm12.chabra.repository;

import com.bm12.chabra.dto.space.SaveSpace;
import com.bm12.chabra.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpaceRepository extends JpaRepository<Space, UUID> {


    @Query("SELECT DISTINCT s FROM space s " +
            "JOIN s.list l " +
            "JOIN l.tasks t " +
            "JOIN t.responsibles r " +
            "WHERE r.id = :userId")
    List<Space> findSpacesByUserResponsavel(@Param("userId") UUID userId);
}
