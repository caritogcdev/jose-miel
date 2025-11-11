package com.josemiel.nicho_nativo_romeral.domain.repositories.tours;

import org.springframework.data.jpa.repository.JpaRepository;
import com.josemiel.nicho_nativo_romeral.domain.entities.Tour;

public interface TourRepository extends JpaRepository<Tour, Integer> {

}
