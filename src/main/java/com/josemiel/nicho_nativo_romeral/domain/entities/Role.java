package com.josemiel.nicho_nativo_romeral.domain.entities;

import com.josemiel.nicho_nativo_romeral.domain.entities.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(name = "uk_roles_name", columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * La anotación @EqualsAndHashCode(onlyExplicitlyIncluded = true) de Lombok
 * sirve para generar automáticamente los métodos equals() y hashCode()
 * basándose únicamente en los campos que se marquen explícitamente
 * con la anotación @EqualsAndHashCode.Include
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

public class Role extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    // @Column(name = "created_at")
    // private LocalDateTime createdAt;

    // @Column(name = "updated_at")
    // private LocalDateTime updatedAt;

    /**
     * Callbacks en JPA @PrePersist y @PreUpdate
     * Pero en en vez de utilizar callbacks,
     * utilizaré Spring Data JPA por buenas prácticas
     */

    // @PrePersist
    // void prePersist() {
    // createdAt = updatedAt = LocalDateTime.now();
    // }

    // @PreUpdate
    // void preUpdate() {
    // updatedAt = LocalDateTime.now();
    // }

}