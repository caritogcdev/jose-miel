package com.josemiel.nicho_nativo_romeral.domain.entities;

import com.josemiel.nicho_nativo_romeral.domain.entities.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
// @Table(name = "users", uniqueConstraints = @UniqueConstraint(name =
// "uk_users_email", columnNames = "email"))
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_users_doc", columnNames = "doc_number")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * @ToString genera automáticamente toString().
 *           En entidades JPA, incluir relaciones (especialmente bidireccionales
 *           o LAZY) en toString() puede:
 *           Disparar cargas perezosas (pega a la BD sin que te des cuenta).
 *           Causar recursión infinita y StackOverflowError (A→B→A…).
 *           Por eso se suele excluir relaciones.
 */
@ToString(exclude = "role")
// @ToString(onlyExplicitlyIncluded = true) // control estricto
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)

public class User extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "doc_number", length = 20)
    private String docNumber;

    @Column(name = "document_type", length = 50)
    private String documentType; // CC, Pasaporte, etc

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "email", length = 60, nullable = false)
    private String email;

    @Column(name = "password_hash", length = 300, nullable = false)
    private String passwordHash;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private boolean active = true;

    // Relaciones con otras entidades
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    /**
     * reference o constraint column (esta anotación hace la relación del
     * constraint), name para colocarle como se va a llamar y referencedColumnName
     * para colocar de donde viene, es decir, el nombre de la llave primaria,
     * columnDefinition es para especificarle un nombre en específico
     */
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role; // un solo rol por usuario (según el modelo ER)

}
