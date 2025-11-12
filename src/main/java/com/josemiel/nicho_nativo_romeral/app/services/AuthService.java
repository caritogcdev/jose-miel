package com.josemiel.nicho_nativo_romeral.app.services;

import com.josemiel.nicho_nativo_romeral.domain.entities.User;
import com.josemiel.nicho_nativo_romeral.domain.repositories.users.RoleRepository;
import com.josemiel.nicho_nativo_romeral.domain.repositories.users.UserRepository;
import com.josemiel.nicho_nativo_romeral.web.dto.request.RegisterUserRequest;
import com.josemiel.nicho_nativo_romeral.web.dto.response.UserResponse;
import com.josemiel.nicho_nativo_romeral.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MailService mailService;

    @Transactional
    public UserResponse register(RegisterUserRequest req) {
        if (userRepo.existsByEmail(req.email())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        if (userRepo.existsByDocNumber(req.docNumber())) {
            throw new IllegalArgumentException("El documento ya está registrado");
        }

        User user = userMapper.fromRegisterRequest(req);
        user.setRole(roleRepo.findById(req.roleId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")));
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        user = userRepo.save(user);

        mailService.sendWelcome(user);
        return userMapper.toResponse(user);
    }

    // Login mínimo (si no usas AuthenticationManager todavía)
    public User loginRaw(String email, String rawPassword) {
        User u = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
        if (!passwordEncoder.matches(rawPassword, u.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        return u;
    }
}
