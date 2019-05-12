package com.epitech.pgt2019.web.rest;

import com.epitech.pgt2019.FeedServiceApp;

import com.epitech.pgt2019.domain.UserFeed;
import com.epitech.pgt2019.repository.UserFeedRepository;
import com.epitech.pgt2019.service.UserFeedService;
import com.epitech.pgt2019.service.dto.UserFeedDTO;
import com.epitech.pgt2019.service.mapper.UserFeedMapper;
import com.epitech.pgt2019.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.List;


import static com.epitech.pgt2019.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserFeedResource REST controller.
 *
 * @see UserFeedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeedServiceApp.class)
public class UserFeedResourceIntTest {

    @Autowired
    private UserFeedRepository userFeedRepository;

    @Autowired
    private UserFeedMapper userFeedMapper;

    @Autowired
    private UserFeedService userFeedService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restUserFeedMockMvc;

    private UserFeed userFeed;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserFeedResource userFeedResource = new UserFeedResource(userFeedService);
        this.restUserFeedMockMvc = MockMvcBuilders.standaloneSetup(userFeedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFeed createEntity() {
        UserFeed userFeed = new UserFeed();
        return userFeed;
    }

    @Before
    public void initTest() {
        userFeedRepository.deleteAll();
        userFeed = createEntity();
    }

    @Test
    public void createUserFeed() throws Exception {
        int databaseSizeBeforeCreate = userFeedRepository.findAll().size();

        // Create the UserFeed
        UserFeedDTO userFeedDTO = userFeedMapper.toDto(userFeed);
        restUserFeedMockMvc.perform(post("/api/user-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFeedDTO)))
            .andExpect(status().isCreated());

        // Validate the UserFeed in the database
        List<UserFeed> userFeedList = userFeedRepository.findAll();
        assertThat(userFeedList).hasSize(databaseSizeBeforeCreate + 1);
        UserFeed testUserFeed = userFeedList.get(userFeedList.size() - 1);
    }

    @Test
    public void createUserFeedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userFeedRepository.findAll().size();

        // Create the UserFeed with an existing ID
        userFeed.setId("existing_id");
        UserFeedDTO userFeedDTO = userFeedMapper.toDto(userFeed);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserFeedMockMvc.perform(post("/api/user-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFeedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserFeed in the database
        List<UserFeed> userFeedList = userFeedRepository.findAll();
        assertThat(userFeedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllUserFeeds() throws Exception {
        // Initialize the database
        userFeedRepository.save(userFeed);

        // Get all the userFeedList
        restUserFeedMockMvc.perform(get("/api/user-feeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userFeed.getId())));
    }
    
    @Test
    public void getUserFeed() throws Exception {
        // Initialize the database
        userFeedRepository.save(userFeed);

        // Get the userFeed
        restUserFeedMockMvc.perform(get("/api/user-feeds/{id}", userFeed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userFeed.getId()));
    }

    @Test
    public void getNonExistingUserFeed() throws Exception {
        // Get the userFeed
        restUserFeedMockMvc.perform(get("/api/user-feeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserFeed() throws Exception {
        // Initialize the database
        userFeedRepository.save(userFeed);

        int databaseSizeBeforeUpdate = userFeedRepository.findAll().size();

        // Update the userFeed
        UserFeed updatedUserFeed = userFeedRepository.findById(userFeed.getId()).get();
        UserFeedDTO userFeedDTO = userFeedMapper.toDto(updatedUserFeed);

        restUserFeedMockMvc.perform(put("/api/user-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFeedDTO)))
            .andExpect(status().isOk());

        // Validate the UserFeed in the database
        List<UserFeed> userFeedList = userFeedRepository.findAll();
        assertThat(userFeedList).hasSize(databaseSizeBeforeUpdate);
        UserFeed testUserFeed = userFeedList.get(userFeedList.size() - 1);
    }

    @Test
    public void updateNonExistingUserFeed() throws Exception {
        int databaseSizeBeforeUpdate = userFeedRepository.findAll().size();

        // Create the UserFeed
        UserFeedDTO userFeedDTO = userFeedMapper.toDto(userFeed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserFeedMockMvc.perform(put("/api/user-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFeedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserFeed in the database
        List<UserFeed> userFeedList = userFeedRepository.findAll();
        assertThat(userFeedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUserFeed() throws Exception {
        // Initialize the database
        userFeedRepository.save(userFeed);

        int databaseSizeBeforeDelete = userFeedRepository.findAll().size();

        // Delete the userFeed
        restUserFeedMockMvc.perform(delete("/api/user-feeds/{id}", userFeed.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserFeed> userFeedList = userFeedRepository.findAll();
        assertThat(userFeedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFeed.class);
        UserFeed userFeed1 = new UserFeed();
        userFeed1.setId("id1");
        UserFeed userFeed2 = new UserFeed();
        userFeed2.setId(userFeed1.getId());
        assertThat(userFeed1).isEqualTo(userFeed2);
        userFeed2.setId("id2");
        assertThat(userFeed1).isNotEqualTo(userFeed2);
        userFeed1.setId(null);
        assertThat(userFeed1).isNotEqualTo(userFeed2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFeedDTO.class);
        UserFeedDTO userFeedDTO1 = new UserFeedDTO();
        userFeedDTO1.setId("id1");
        UserFeedDTO userFeedDTO2 = new UserFeedDTO();
        assertThat(userFeedDTO1).isNotEqualTo(userFeedDTO2);
        userFeedDTO2.setId(userFeedDTO1.getId());
        assertThat(userFeedDTO1).isEqualTo(userFeedDTO2);
        userFeedDTO2.setId("id2");
        assertThat(userFeedDTO1).isNotEqualTo(userFeedDTO2);
        userFeedDTO1.setId(null);
        assertThat(userFeedDTO1).isNotEqualTo(userFeedDTO2);
    }
}
