package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Glasses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Glasses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GlassesRepository extends JpaRepository<Glasses, Long> {}
