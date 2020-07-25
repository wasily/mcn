package me.local.mcn.services.release;

import me.local.mcn.domain.model.Release;

import java.util.List;

public interface ReleaseService {
    List<Release> getReleases(String title);
}
