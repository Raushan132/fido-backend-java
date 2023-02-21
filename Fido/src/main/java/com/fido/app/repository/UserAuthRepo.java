package com.fido.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fido.app.entity.UserAuth;

@Repository
public interface UserAuthRepo extends JpaRepository<UserAuth, Long> {
	   Optional<UserAuth> findByEmail(String email);

}
