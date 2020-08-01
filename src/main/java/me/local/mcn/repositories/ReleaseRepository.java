package me.local.mcn.repositories;

import me.local.mcn.domain.model.Release;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReleaseRepository extends MongoRepository<Release, String>, ReleaseRepositoryCustom {
    @Query("{ 'title' : {$regex:?0, $options:'i'}}")
    List<Release> findByTitleContaining(String title);
}
