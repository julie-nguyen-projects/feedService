package com.epitech.pgt2019.web.rest;
import com.epitech.pgt2019.service.UserFeedService;
import com.epitech.pgt2019.web.rest.errors.BadRequestAlertException;
import com.epitech.pgt2019.web.rest.util.HeaderUtil;
import com.epitech.pgt2019.service.dto.UserFeedDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserFeed.
 */
@RestController
@RequestMapping("/api")
public class UserFeedResource {

    private final Logger log = LoggerFactory.getLogger(UserFeedResource.class);

    private static final String ENTITY_NAME = "feedServiceUserFeed";

    private final UserFeedService userFeedService;

    public UserFeedResource(UserFeedService userFeedService) {
        this.userFeedService = userFeedService;
    }

    /**
     * POST  /user-feeds : Create a new userFeed.
     *
     * @param newId the new id of the userFeed
     * @param userFeedDTO the userFeedDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userFeedDTO, or with status 400 (Bad Request) if the userFeed has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-feeds/{newId}")
    public ResponseEntity<UserFeedDTO> createUserFeed(@PathVariable("newId") String newId, @RequestBody UserFeedDTO userFeedDTO) throws URISyntaxException {
        log.debug("REST request to save UserFeed : {}", userFeedDTO);
        if (userFeedDTO.getId() != null) {
            throw new BadRequestAlertException("A new userFeed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserFeedDTO result = userFeedService.save(newId, userFeedDTO);
        return ResponseEntity.created(new URI("/api/user-feeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-feeds : Updates an existing userFeed.
     *
     * @param userFeedDTO the userFeedDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userFeedDTO,
     * or with status 400 (Bad Request) if the userFeedDTO is not valid,
     * or with status 500 (Internal Server Error) if the userFeedDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-feeds/{newId}")
    public ResponseEntity<UserFeedDTO> updateUserFeed(@PathVariable("newId") String newId, @RequestBody UserFeedDTO userFeedDTO) throws URISyntaxException {
        log.debug("REST request to update UserFeed : {}", userFeedDTO);
        if (userFeedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserFeedDTO result = userFeedService.save(newId, userFeedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userFeedDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-feeds : get all the userFeeds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userFeeds in body
     */
    @GetMapping("/user-feeds")
    public List<UserFeedDTO> getAllUserFeeds() {
        log.debug("REST request to get all UserFeeds");
        return userFeedService.findAll();
    }

    /**
     * GET  /user-feeds/:id : get the "id" userFeed.
     *
     * @param id the id of the userFeedDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userFeedDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-feeds/{id}")
    public ResponseEntity<UserFeedDTO> getUserFeed(@PathVariable String id) {
        log.debug("REST request to get UserFeed : {}", id);
        Optional<UserFeedDTO> userFeedDTO = userFeedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userFeedDTO);
    }

    /**
     * DELETE  /user-feeds/:id : delete the "id" userFeed.
     *
     * @param id the id of the userFeedDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-feeds/{id}")
    public ResponseEntity<Void> deleteUserFeed(@PathVariable String id) {
        log.debug("REST request to delete UserFeed : {}", id);
        userFeedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
