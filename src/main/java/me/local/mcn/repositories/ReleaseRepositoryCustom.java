package me.local.mcn.repositories;

import me.local.mcn.domain.model.Release;

public interface ReleaseRepositoryCustom {
   long getMostRecentReleaseUnixtime();
    void upsert(Release release);
}
