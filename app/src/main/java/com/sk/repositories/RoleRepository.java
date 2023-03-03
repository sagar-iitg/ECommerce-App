/**
 * @author
 * Sagar Kumar
 */
package com.sk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sk.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

}
