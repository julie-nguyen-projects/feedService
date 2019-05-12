package com.epitech.pgt2019.repository;

import com.epitech.pgt2019.domain.Post;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends MongoRepository<Post, String> {

}
