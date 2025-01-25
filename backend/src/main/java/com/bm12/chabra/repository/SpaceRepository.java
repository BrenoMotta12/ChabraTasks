package com.bm12.chabra.repository;

import com.bm12.chabra.dto.space.SaveSpace;
import com.bm12.chabra.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpaceRepository extends JpaRepository<Space, UUID> {

}
