package com.bm12.chabra.repository;

import com.bm12.chabra.model.Priority;
import com.bm12.chabra.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<Status, UUID>{
}

