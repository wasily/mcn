package me.local.mcn.repositories;

import me.local.mcn.domain.model.Release;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReleaseRepositoryCustomImplTest {
    @Autowired
    private ReleaseRepository releaseRepository;

    @Test
    void shouldReturnMostRecentReleaseTime() {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(3);
        var release1 = new Release("id1", "title42", 1234, "hash1", LocalDateTime.now(zoneOffset).minusSeconds(3));
        var release2 = new Release("id2", "other film", 1234, "hash1", LocalDateTime.now(zoneOffset).minusHours(3));
        var mostRecent = new Release("id3", "TITLE of smth", 1234, "hash1", LocalDateTime.now(zoneOffset));
        releaseRepository.save(release1);
        releaseRepository.save(release2);
        releaseRepository.save(mostRecent);
        assertEquals(mostRecent.getReleaseTime().toEpochSecond(zoneOffset), releaseRepository.getMostRecentReleaseUnixtime());
    }

    @Test
    void shouldReturnReleasesByTitleContaining() {
        var release1 = new Release("id1", "title42", 1234, "hash1", LocalDateTime.now().minusSeconds(3));
        var release2 = new Release("id2", "other film", 12345, "hash2", LocalDateTime.now().minusHours(3));
        var mostRecent = new Release("id3", "TITLE of smth", 123456, "hash3", LocalDateTime.now());
        releaseRepository.save(release1);
        releaseRepository.save(release2);
        releaseRepository.save(mostRecent);
        var releases = releaseRepository.findByTitleContaining("title");
        assertThat(releases).hasSize(2).usingElementComparatorIgnoringFields("releaseTime").containsExactlyInAnyOrder(release1, mostRecent);
    }

    @Test
    void shouldReturnReleasesByTime() {
        LocalDateTime time = LocalDateTime.now().minusMinutes(1);
        var release1 = new Release("id1", "title42", 1234, "hash1", LocalDateTime.now().minusSeconds(30));
        var release2 = new Release("id2", "other film", 12345, "hash2", LocalDateTime.now().minusHours(3));
        var mostRecent = new Release("id3", "TITLE of smth", 123456, "hash3", LocalDateTime.now());
        releaseRepository.save(release1);
        releaseRepository.save(release2);
        releaseRepository.save(mostRecent);
        var releases = releaseRepository.findByTitleContainingAndReleaseTimeGreaterThan("title", time);
        assertThat(releases).hasSize(2).usingElementComparatorIgnoringFields("releaseTime").containsOnly(release1, mostRecent);
    }

    @Test
    void shouldUpsert() {
        var release = new Release("id1", "title42", 1234, "hash1", LocalDateTime.now().minusDays(3));
        var updatedRelease = new Release("id1", "updated title", 1234345, "hash909",
                LocalDateTime.of(2020, 12, 1, 12, 50));
        releaseRepository.save(release);
        releaseRepository.upsert(updatedRelease);
        var result = releaseRepository.findAll();
        assertThat(result).hasSize(1).usingFieldByFieldElementComparator().containsExactly(updatedRelease);
    }

}