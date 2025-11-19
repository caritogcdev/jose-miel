package com.josemiel.nicho_nativo_romeral.infrastructure;

import com.josemiel.nicho_nativo_romeral.domain.entities.Role;
import com.josemiel.nicho_nativo_romeral.domain.repositories.users.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) {
        initRole("USER");
        initRole("ADMIN");
    }

    private void initRole(String name) {
        roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = Role.builder()
                            .name(name)
                            .build();
                    return roleRepository.save(role);
                });
    }
}
