package com.elasticsearchtask.repository.jpa;

import com.elasticsearchtask.model.entity.FundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundRepository extends JpaRepository<FundEntity, Long> {
}
