package me.local.mcn.repositories;

import me.local.mcn.domain.model.Release;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReleaseRepository extends MongoRepository<Release, String> {
    List<Release> findByTitle(String title);
}
