package com.josemiel.nicho_nativo_romeral.domain.repositories.users;

import com.josemiel.nicho_nativo_romeral.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Encuentra un usuario por su correo electrónico.
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Verifica si un usuario existe por su correo electrónico.
     * @param email
     * @return
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si un usuario existe por su número de documento.
     * @param docNumber
     * @return
     */
    boolean existsByDocNumber(String docNumber);
}
