package com.josemiel.nicho_nativo_romeral.infrastructure.security;

import com.josemiel.nicho_nativo_romeral.domain.repositories.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    /** 
     * Carga los detalles del usuario por su email.
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var u = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        var authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + u.getRole().getName().toUpperCase())
        );

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getPasswordHash())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(!u.isActive())
                .credentialsExpired(false)
                .disabled(!u.isActive())
                .build();
    }
    
}
