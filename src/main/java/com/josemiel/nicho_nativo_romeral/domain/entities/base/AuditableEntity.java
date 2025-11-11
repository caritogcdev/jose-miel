package com.josemiel.nicho_nativo_romeral.domain.entities.base;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Creamos una superclase para manejar los campos de auditoría
 * o Timestamps (createdAt, updatedAt, deletedAt)
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter

public abstract class AuditableEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false) // nullable = false
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at") //  nullable = false
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // implementar lógica para soft delete
}
