package com.bm12.chabra.repository;

import com.bm12.chabra.model.ListTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListRepository extends JpaRepository<ListTask, UUID>{
}