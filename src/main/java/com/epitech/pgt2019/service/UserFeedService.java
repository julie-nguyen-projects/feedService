package com.epitech.pgt2019.service;

import com.epitech.pgt2019.domain.UserFeed;
import com.epitech.pgt2019.repository.UserFeedRepository;
import com.epitech.pgt2019.service.dto.UserFeedDTO;
import com.epitech.pgt2019.service.mapper.UserFeedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserFeed.
 */
@Service
public class UserFeedService {

    private final Logger log = LoggerFactory.getLogger(UserFeedService.class);

    private final UserFeedRepository userFeedRepository;

    private final UserFeedMapper userFeedMapper;

    public UserFeedService(UserFeedRepository userFeedRepository, UserFeedMapper userFeedMapper) {
        this.userFeedRepository = userFeedRepository;
        this.userFeedMapper = userFeedMapper;
    }

    /**
     * Save a userFeed.
     *
     * @param userFeedDTO the entity to save
     * @return the persisted entity
     */
    public UserFeedDTO save(UserFeedDTO userFeedDTO) {
        log.debug("Request to save UserFeed : {}", userFeedDTO);
        UserFeed userFeed = userFeedMapper.toEntity(userFeedDTO);
        userFeed = userFeedRepository.save(userFeed);
        return userFeedMapper.toDto(userFeed);
    }

    /**
     * Get all the userFeeds.
     *
     * @return the list of entities
     */
    public List<UserFeedDTO> findAll() {
        log.debug("Request to get all UserFeeds");
        return userFeedRepository.findAll().stream()
            .map(userFeedMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userFeed by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<UserFeedDTO> findOne(String id) {
        log.debug("Request to get UserFeed : {}", id);
        return userFeedRepository.findById(id)
            .map(userFeedMapper::toDto);
    }

    /**
     * Delete the userFeed by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete UserFeed : {}", id);
        userFeedRepository.deleteById(id);
    }
}
