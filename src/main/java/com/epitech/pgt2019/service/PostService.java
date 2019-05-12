package com.epitech.pgt2019.service;

import com.epitech.pgt2019.domain.Post;
import com.epitech.pgt2019.repository.PostRepository;
import com.epitech.pgt2019.service.dto.PostDTO;
import com.epitech.pgt2019.service.mapper.PostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Post.
 */
@Service
public class PostService {

    private final Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    /**
     * Save a post.
     *
     * @param postDTO the entity to save
     * @return the persisted entity
     */
    public PostDTO save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    /**
     * Get all the posts.
     *
     * @return the list of entities
     */
    public List<PostDTO> findAll() {
        log.debug("Request to get all Posts");
        return postRepository.findAll().stream()
            .map(postMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one post by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<PostDTO> findOne(String id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id)
            .map(postMapper::toDto);
    }

    /**
     * Delete the post by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }
}
