package me.local.mcn.services.release;

import lombok.RequiredArgsConstructor;
import me.local.mcn.domain.model.Release;
import me.local.mcn.repositories.ReleaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseServiceImpl implements ReleaseService {
    private final ReleaseRepository releaseRepository;

    @Override
    public List<Release> getReleases(String title) {
        return releaseRepository.findByTitleContaining(title);
    }

    @Override
    public List<Release> getReleasesByTime(String title, LocalDateTime localDateTime) {
        return releaseRepository.findByTitleContainingAndReleaseTimeGreaterThan(title, localDateTime);
    }


}
