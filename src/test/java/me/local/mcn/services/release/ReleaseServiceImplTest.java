package me.local.mcn.services.release;

import me.local.mcn.domain.model.Release;
import me.local.mcn.repositories.ReleaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReleaseServiceImplTest {
    @Mock
    private ReleaseRepository releaseRepository;

    private ReleaseService releaseService;

    @BeforeEach
    void setUp() {
        releaseService = new ReleaseServiceImpl(releaseRepository);
    }

    @Test
    void shouldCallFindByTitleMethodWhenSearchingByTitleContaining() {
        var release1 = new Release("id1","title1", 123, "hash1", LocalDateTime.now().minusDays(1));
        var release2 = new Release("id2","title2", 124, "hash2", LocalDateTime.now());
        List<Release> releases = List.of(release1, release2);
        when(releaseRepository.findByTitleContaining(anyString())).thenReturn(releases);
        assertThat(releases).containsAll(releaseService.getReleases("title"));
    }
}