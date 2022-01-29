package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Glasses;
import com.mycompany.myapp.repository.GlassesRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GlassesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GlassesResourceIT {

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_FRONT = "AAAAAAAAAA";
    private static final String UPDATED_FRONT = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLES = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLES = "BBBBBBBBBB";

    private static final String DEFAULT_LENSES = "AAAAAAAAAA";
    private static final String UPDATED_LENSES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/glasses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GlassesRepository glassesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGlassesMockMvc;

    private Glasses glasses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Glasses createEntity(EntityManager em) {
        Glasses glasses = new Glasses().model(DEFAULT_MODEL).front(DEFAULT_FRONT).temples(DEFAULT_TEMPLES).lenses(DEFAULT_LENSES);
        return glasses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Glasses createUpdatedEntity(EntityManager em) {
        Glasses glasses = new Glasses().model(UPDATED_MODEL).front(UPDATED_FRONT).temples(UPDATED_TEMPLES).lenses(UPDATED_LENSES);
        return glasses;
    }

    @BeforeEach
    public void initTest() {
        glasses = createEntity(em);
    }

    @Test
    @Transactional
    void createGlasses() throws Exception {
        int databaseSizeBeforeCreate = glassesRepository.findAll().size();
        // Create the Glasses
        restGlassesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(glasses))
            )
            .andExpect(status().isCreated());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeCreate + 1);
        Glasses testGlasses = glassesList.get(glassesList.size() - 1);
        assertThat(testGlasses.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testGlasses.getFront()).isEqualTo(DEFAULT_FRONT);
        assertThat(testGlasses.getTemples()).isEqualTo(DEFAULT_TEMPLES);
        assertThat(testGlasses.getLenses()).isEqualTo(DEFAULT_LENSES);
    }

    @Test
    @Transactional
    void createGlassesWithExistingId() throws Exception {
        // Create the Glasses with an existing ID
        glasses.setId(1L);

        int databaseSizeBeforeCreate = glassesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlassesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(glasses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGlasses() throws Exception {
        // Initialize the database
        glassesRepository.saveAndFlush(glasses);

        // Get all the glassesList
        restGlassesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(glasses.getId().intValue())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].front").value(hasItem(DEFAULT_FRONT)))
            .andExpect(jsonPath("$.[*].temples").value(hasItem(DEFAULT_TEMPLES)))
            .andExpect(jsonPath("$.[*].lenses").value(hasItem(DEFAULT_LENSES)));
    }

    @Test
    @Transactional
    void getGlasses() throws Exception {
        // Initialize the database
        glassesRepository.saveAndFlush(glasses);

        // Get the glasses
        restGlassesMockMvc
            .perform(get(ENTITY_API_URL_ID, glasses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(glasses.getId().intValue()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.front").value(DEFAULT_FRONT))
            .andExpect(jsonPath("$.temples").value(DEFAULT_TEMPLES))
            .andExpect(jsonPath("$.lenses").value(DEFAULT_LENSES));
    }

    @Test
    @Transactional
    void getNonExistingGlasses() throws Exception {
        // Get the glasses
        restGlassesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGlasses() throws Exception {
        // Initialize the database
        glassesRepository.saveAndFlush(glasses);

        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();

        // Update the glasses
        Glasses updatedGlasses = glassesRepository.findById(glasses.getId()).get();
        // Disconnect from session so that the updates on updatedGlasses are not directly saved in db
        em.detach(updatedGlasses);
        updatedGlasses.model(UPDATED_MODEL).front(UPDATED_FRONT).temples(UPDATED_TEMPLES).lenses(UPDATED_LENSES);

        restGlassesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGlasses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGlasses))
            )
            .andExpect(status().isOk());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
        Glasses testGlasses = glassesList.get(glassesList.size() - 1);
        assertThat(testGlasses.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testGlasses.getFront()).isEqualTo(UPDATED_FRONT);
        assertThat(testGlasses.getTemples()).isEqualTo(UPDATED_TEMPLES);
        assertThat(testGlasses.getLenses()).isEqualTo(UPDATED_LENSES);
    }

    @Test
    @Transactional
    void putNonExistingGlasses() throws Exception {
        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();
        glasses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGlassesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, glasses.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(glasses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGlasses() throws Exception {
        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();
        glasses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGlassesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(glasses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGlasses() throws Exception {
        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();
        glasses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGlassesMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(glasses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGlassesWithPatch() throws Exception {
        // Initialize the database
        glassesRepository.saveAndFlush(glasses);

        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();

        // Update the glasses using partial update
        Glasses partialUpdatedGlasses = new Glasses();
        partialUpdatedGlasses.setId(glasses.getId());

        partialUpdatedGlasses.model(UPDATED_MODEL).temples(UPDATED_TEMPLES);

        restGlassesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGlasses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGlasses))
            )
            .andExpect(status().isOk());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
        Glasses testGlasses = glassesList.get(glassesList.size() - 1);
        assertThat(testGlasses.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testGlasses.getFront()).isEqualTo(DEFAULT_FRONT);
        assertThat(testGlasses.getTemples()).isEqualTo(UPDATED_TEMPLES);
        assertThat(testGlasses.getLenses()).isEqualTo(DEFAULT_LENSES);
    }

    @Test
    @Transactional
    void fullUpdateGlassesWithPatch() throws Exception {
        // Initialize the database
        glassesRepository.saveAndFlush(glasses);

        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();

        // Update the glasses using partial update
        Glasses partialUpdatedGlasses = new Glasses();
        partialUpdatedGlasses.setId(glasses.getId());

        partialUpdatedGlasses.model(UPDATED_MODEL).front(UPDATED_FRONT).temples(UPDATED_TEMPLES).lenses(UPDATED_LENSES);

        restGlassesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGlasses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGlasses))
            )
            .andExpect(status().isOk());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
        Glasses testGlasses = glassesList.get(glassesList.size() - 1);
        assertThat(testGlasses.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testGlasses.getFront()).isEqualTo(UPDATED_FRONT);
        assertThat(testGlasses.getTemples()).isEqualTo(UPDATED_TEMPLES);
        assertThat(testGlasses.getLenses()).isEqualTo(UPDATED_LENSES);
    }

    @Test
    @Transactional
    void patchNonExistingGlasses() throws Exception {
        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();
        glasses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGlassesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, glasses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(glasses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGlasses() throws Exception {
        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();
        glasses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGlassesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(glasses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGlasses() throws Exception {
        int databaseSizeBeforeUpdate = glassesRepository.findAll().size();
        glasses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGlassesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(glasses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Glasses in the database
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGlasses() throws Exception {
        // Initialize the database
        glassesRepository.saveAndFlush(glasses);

        int databaseSizeBeforeDelete = glassesRepository.findAll().size();

        // Delete the glasses
        restGlassesMockMvc
            .perform(delete(ENTITY_API_URL_ID, glasses.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Glasses> glassesList = glassesRepository.findAll();
        assertThat(glassesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
