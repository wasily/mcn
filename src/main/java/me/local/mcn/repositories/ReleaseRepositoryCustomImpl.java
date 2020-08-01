package me.local.mcn.repositories;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;

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
        return document != null ? document.getDate(fieldName).getTime() / 1000 : 0;
    }
}
