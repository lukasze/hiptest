package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Bike;
import com.mycompany.myapp.repository.BikeRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Bike}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BikeResource {

    private final Logger log = LoggerFactory.getLogger(BikeResource.class);

    private static final String ENTITY_NAME = "bike";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BikeRepository bikeRepository;

    public BikeResource(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    /**
     * {@code POST  /bikes} : Create a new bike.
     *
     * @param bike the bike to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bike, or with status {@code 400 (Bad Request)} if the bike has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bikes")
    public ResponseEntity<Bike> createBike(@RequestBody Bike bike) throws URISyntaxException {
        log.debug("REST request to save Bike : {}", bike);
        if (bike.getId() != null) {
            throw new BadRequestAlertException("A new bike cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bike result = bikeRepository.save(bike);
        return ResponseEntity
            .created(new URI("/api/bikes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bikes/:id} : Updates an existing bike.
     *
     * @param id the id of the bike to save.
     * @param bike the bike to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bike,
     * or with status {@code 400 (Bad Request)} if the bike is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bike couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bikes/{id}")
    public ResponseEntity<Bike> updateBike(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bike bike)
        throws URISyntaxException {
        log.debug("REST request to update Bike : {}, {}", id, bike);
        if (bike.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bike.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bikeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bike result = bikeRepository.save(bike);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bike.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bikes/:id} : Partial updates given fields of an existing bike, field will ignore if it is null
     *
     * @param id the id of the bike to save.
     * @param bike the bike to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bike,
     * or with status {@code 400 (Bad Request)} if the bike is not valid,
     * or with status {@code 404 (Not Found)} if the bike is not found,
     * or with status {@code 500 (Internal Server Error)} if the bike couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bikes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bike> partialUpdateBike(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bike bike)
        throws URISyntaxException {
        log.debug("REST request to partial update Bike partially : {}, {}", id, bike);
        if (bike.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bike.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bikeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bike> result = bikeRepository
            .findById(bike.getId())
            .map(existingBike -> {
                if (bike.getModel() != null) {
                    existingBike.setModel(bike.getModel());
                }
                if (bike.getSerialNo() != null) {
                    existingBike.setSerialNo(bike.getSerialNo());
                }

                return existingBike;
            })
            .map(bikeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bike.getId().toString())
        );
    }

    /**
     * {@code GET  /bikes} : get all the bikes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bikes in body.
     */
    @GetMapping("/bikes")
    public List<Bike> getAllBikes() {
        log.debug("REST request to get all Bikes");
        return bikeRepository.findAll();
    }

    /**
     * {@code GET  /bikes/:id} : get the "id" bike.
     *
     * @param id the id of the bike to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bike, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bikes/{id}")
    public ResponseEntity<Bike> getBike(@PathVariable Long id) {
        log.debug("REST request to get Bike : {}", id);
        Optional<Bike> bike = bikeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bike);
    }

    /**
     * {@code DELETE  /bikes/:id} : delete the "id" bike.
     *
     * @param id the id of the bike to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bikes/{id}")
    public ResponseEntity<Void> deleteBike(@PathVariable Long id) {
        log.debug("REST request to delete Bike : {}", id);
        bikeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
