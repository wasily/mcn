package me.local.mcn.repositories;

import lombok.RequiredArgsConstructor;
import me.local.mcn.domain.model.Release;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@RequiredArgsConstructor
public class ReleaseRepositoryCustomImpl implements ReleaseRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public long getMostRecentReleaseUnixtime() {
        String fieldName = "mostRecentDate";
        String releaseTimeFieldName = "releaseTime";
        String collectionName = "releases";
        GroupOperation mostRecent = group().max(releaseTimeFieldName).as(fieldName);
        Aggregation aggregation = newAggregation(mostRecent);
        AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, collectionName, Document.class);
        Document document = result.getUniqueMappedResult();
        return document != null ? document.getDate(fieldName).toInstant().getEpochSecond() : 0;
    }

    @Override
    public void upsert(Release release) {
        Query query = new Query();
        query.addCriteria(Criteria.where("trackerId").is(release.getTrackerId()));
        Update update = new Update();
        update.set("title", release.getTitle());
        update.set("size", release.getSize());
        update.set("infoHash", release.getInfoHash());
        update.set("releaseTime", release.getReleaseTime());
        mongoTemplate.upsert(query, update, Release.class);
    }
}
