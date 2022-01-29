package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Truck;
import com.mycompany.myapp.repository.TruckRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Truck}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TruckResource {

    private final Logger log = LoggerFactory.getLogger(TruckResource.class);

    private static final String ENTITY_NAME = "truck";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TruckRepository truckRepository;

    public TruckResource(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    /**
     * {@code POST  /trucks} : Create a new truck.
     *
     * @param truck the truck to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new truck, or with status {@code 400 (Bad Request)} if the truck has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trucks")
    public ResponseEntity<Truck> createTruck(@RequestBody Truck truck) throws URISyntaxException {
        log.debug("REST request to save Truck : {}", truck);
        if (truck.getId() != null) {
            throw new BadRequestAlertException("A new truck cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Truck result = truckRepository.save(truck);
        return ResponseEntity
            .created(new URI("/api/trucks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trucks/:id} : Updates an existing truck.
     *
     * @param id the id of the truck to save.
     * @param truck the truck to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated truck,
     * or with status {@code 400 (Bad Request)} if the truck is not valid,
     * or with status {@code 500 (Internal Server Error)} if the truck couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trucks/{id}")
    public ResponseEntity<Truck> updateTruck(@PathVariable(value = "id", required = false) final Long id, @RequestBody Truck truck)
        throws URISyntaxException {
        log.debug("REST request to update Truck : {}, {}", id, truck);
        if (truck.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, truck.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!truckRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Truck result = truckRepository.save(truck);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, truck.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trucks/:id} : Partial updates given fields of an existing truck, field will ignore if it is null
     *
     * @param id the id of the truck to save.
     * @param truck the truck to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated truck,
     * or with status {@code 400 (Bad Request)} if the truck is not valid,
     * or with status {@code 404 (Not Found)} if the truck is not found,
     * or with status {@code 500 (Internal Server Error)} if the truck couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trucks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Truck> partialUpdateTruck(@PathVariable(value = "id", required = false) final Long id, @RequestBody Truck truck)
        throws URISyntaxException {
        log.debug("REST request to partial update Truck partially : {}, {}", id, truck);
        if (truck.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, truck.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!truckRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Truck> result = truckRepository
            .findById(truck.getId())
            .map(existingTruck -> {
                if (truck.getModel() != null) {
                    existingTruck.setModel(truck.getModel());
                }
                if (truck.getEngine() != null) {
                    existingTruck.setEngine(truck.getEngine());
                }
                if (truck.getSerialNo() != null) {
                    existingTruck.setSerialNo(truck.getSerialNo());
                }

                return existingTruck;
            })
            .map(truckRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, truck.getId().toString())
        );
    }

    /**
     * {@code GET  /trucks} : get all the trucks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trucks in body.
     */
    @GetMapping("/trucks")
    public List<Truck> getAllTrucks(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Trucks");
        return truckRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /trucks/:id} : get the "id" truck.
     *
     * @param id the id of the truck to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the truck, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trucks/{id}")
    public ResponseEntity<Truck> getTruck(@PathVariable Long id) {
        log.debug("REST request to get Truck : {}", id);
        Optional<Truck> truck = truckRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(truck);
    }

    /**
     * {@code DELETE  /trucks/:id} : delete the "id" truck.
     *
     * @param id the id of the truck to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trucks/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        log.debug("REST request to delete Truck : {}", id);
        truckRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
