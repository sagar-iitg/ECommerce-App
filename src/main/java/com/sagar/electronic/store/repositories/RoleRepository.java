package com.sagar.electronic.store.repositories;

import com.sagar.electronic.store.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
