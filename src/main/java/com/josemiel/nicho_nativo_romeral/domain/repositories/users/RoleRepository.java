package com.josemiel.nicho_nativo_romeral.domain.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import com.josemiel.nicho_nativo_romeral.domain.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Encuentra un rol por su nombre.
     * @param name
     * @return
     */
    Optional<Role> findByName(String name);
}
