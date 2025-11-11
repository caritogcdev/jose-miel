package com.josemiel.nicho_nativo_romeral.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Activamos Spring Auditing para los Timestamps
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

}
