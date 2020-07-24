package me.local.mcn.repositories;

import me.local.mcn.domain.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    Movie findByImdbId(String imdbId);
    @Query("{ 'primaryTitle' : {$regex:?0, $options:'i'}}")
    List<Movie> findByPrimaryTitleContaining(String title);
}
