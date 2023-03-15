package com.fido.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fido.app.entity.DebitHistory;

@Repository
public interface DebitHistoryRepo extends JpaRepository<DebitHistory, Long> {
	
	List<DebitHistory> findAllByCustomerId(long id);
	
}
