package com.fido.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fido.app.entity.CardDetail;

public interface CardRepo extends JpaRepository<CardDetail, Long> {

}
