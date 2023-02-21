package com.fido.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fido.app.entity.Role;

public interface RoleRepo extends JpaRepository<Role,Long> {

}
