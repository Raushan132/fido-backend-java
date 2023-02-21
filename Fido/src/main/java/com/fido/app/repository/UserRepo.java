package com.fido.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fido.app.entity.User_Detail;

@Repository
public interface UserRepo extends JpaRepository<User_Detail, Long>{
	  public String findFullNameById(long id); 
}
