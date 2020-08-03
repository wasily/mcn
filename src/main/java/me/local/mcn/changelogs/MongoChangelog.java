package me.local.mcn.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import me.local.mcn.domain.model.Event;
import me.local.mcn.domain.model.Release;
import me.local.mcn.domain.model.Subscription;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Index;

@ChangeLog(order = "000")
public class MongoChangelog {
    @ChangeSet(order = "001", id = "index creation", author = "wz")
    public void addIndexes(MongockTemplate template) {
        template.indexOps(Release.class).ensureIndex(new Index().unique().on("trackerId", Sort.Direction.ASC));
        template.indexOps(Release.class).ensureIndex(new Index().on("title", Sort.Direction.ASC));
        template.indexOps(Release.class).ensureIndex(new Index().on("releaseTime", Sort.Direction.ASC));
        template.indexOps(Event.class).ensureIndex(new Index().on("eventTime", Sort.Direction.ASC));
        template.indexOps(Subscription.class).ensureIndex(new Index().unique()
                .on("email", Sort.Direction.ASC)
                .on("title", Sort.Direction.ASC));
    }
}
