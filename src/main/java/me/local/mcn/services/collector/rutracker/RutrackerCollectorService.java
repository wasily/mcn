package me.local.mcn.services.collector.rutracker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.local.mcn.domain.model.Release;
import me.local.mcn.repositories.ReleaseRepository;
import me.local.mcn.services.collector.CollectorService;
import me.local.mcn.services.collector.rutracker.response.ReleaseResponse;
import me.local.mcn.services.collector.rutracker.response.SubforumDetailsResponse;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RutrackerCollectorService implements CollectorService {
    private final ReleaseRepository releaseRepository;
    private final RutrackerSettings rutrackerSettings = new RutrackerSettings();
    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
    private long mostRecentReleaseUnixtime = 0;
    private final MongoTemplate mongoTemplate;

    @Override
    public void collect() {
        mostRecentReleaseUnixtime = releaseRepository.getMostRecentReleaseUnixtime();
        List<String> releasesIds = getReleasesIds(rutrackerSettings.getMoviesSubforums());
        releasesIds.addAll(getSeriesSubforums(rutrackerSettings.getSeriesForums()));
        int chunksCount = (releasesIds.size() + rutrackerSettings.getRequestLimit() - 1) / rutrackerSettings.getRequestLimit();
        IntStream.range(0, chunksCount).parallel().mapToObj(i -> combineIds(releasesIds, i))
                .forEach(ids -> requestReleases(restTemplate, ids).thenAccept(releases -> releases.forEach(this::saveRelease)));
    }

    private List<String> getSeriesSubforums(List<String> forumIds) {
        String seriesCategoryId = "18";//18	- "Сериалы"
        ResponseEntity<String> forumTreeResponse = restTemplate.getForEntity(rutrackerSettings.getTreeUrl(), String.class);
        List<String> subforumsIds = new ArrayList<>();
        forumIds.forEach(forumId -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode forum = mapper.readTree(forumTreeResponse.getBody()).path("result").get("tree").get(seriesCategoryId).get(forumId);
                        forum.iterator().forEachRemaining(x -> subforumsIds.add(x.toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        return subforumsIds;
    }

    private List<String> getReleasesIds(List<String> subforumsIds) {
        int requiredArrayLength = 5;
        int regTimeIdx = 2;
        var releasesIds = new ArrayList<String>(301);
        subforumsIds.forEach(id -> {
            try {
                releasesIds.addAll(restTemplate.getForObject(rutrackerSettings.getSubforumsUrl() + id, SubforumDetailsResponse.class)
                        .getResult().entrySet().stream()
                        .filter(entry -> entry.getValue().length == requiredArrayLength)
                        .filter(entry -> entry.getValue()[regTimeIdx] > mostRecentReleaseUnixtime)
                        .map(Map.Entry::getKey).collect(Collectors.toList()));
            } catch (RestClientException e) {
                if (!(e instanceof HttpClientErrorException.NotFound)) {
                    e.printStackTrace();
                }
            }
        });
        return releasesIds;
    }

    private String combineIds(List<String> topicsIds, int chunkNumber) {
        return String.join(",", topicsIds.subList(chunkNumber * rutrackerSettings.getRequestLimit(),
                Math.min(rutrackerSettings.getRequestLimit() * (chunkNumber + 1), topicsIds.size())));
    }

    private CompletableFuture<List<Release>> requestReleases(RestTemplate template, String joinedTopicsIds) {
        return CompletableFuture.supplyAsync(() -> template.getForObject(rutrackerSettings.getReleasesUrl() + joinedTopicsIds, ReleaseResponse.class))
                .thenApplyAsync(releaseResponse -> {
                    var result = releaseResponse.getResult();
                    return result.entrySet().stream().filter(entry -> entry.getValue() != null)
                            .map(entry -> {
                                var value = entry.getValue();
                                return new Release(entry.getKey(), value.getTitle(), value.getSize(), value.getInfoHash(), LocalDateTime.ofEpochSecond(value.getReleaseTime(), 0, ZoneOffset.UTC));
                            }).collect(Collectors.toList());
                });
    }

    private void saveRelease(Release release) {
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
