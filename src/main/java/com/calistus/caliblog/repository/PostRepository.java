package com.calistus.caliblog.repository;


import com.calistus.caliblog.data.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Optional<Post> findById(String id);
    List<Post> findAllByPublishedTrue();

}
