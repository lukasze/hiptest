package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Truck;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Truck entity.
 */
@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
    @Query(
        value = "select distinct truck from Truck truck left join fetch truck.drivers",
        countQuery = "select count(distinct truck) from Truck truck"
    )
    Page<Truck> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct truck from Truck truck left join fetch truck.drivers")
    List<Truck> findAllWithEagerRelationships();

    @Query("select truck from Truck truck left join fetch truck.drivers where truck.id =:id")
    Optional<Truck> findOneWithEagerRelationships(@Param("id") Long id);
}
