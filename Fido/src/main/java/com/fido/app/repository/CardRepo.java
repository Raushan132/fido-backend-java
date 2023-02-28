package com.fido.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fido.app.entity.CardDetail;

public interface CardRepo extends JpaRepository<CardDetail, Long> {
       List<CardDetail> findByCustomerId(long id);
}
