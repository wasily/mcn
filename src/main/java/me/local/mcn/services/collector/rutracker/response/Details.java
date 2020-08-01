package me.local.mcn.services.collector.rutracker.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Details {
    @JsonProperty("topic_title")
    private String title;

    @JsonProperty("size")
    private long size;

    @JsonProperty("infoHash")
    private String infoHash;

    @JsonProperty("reg_time")
    private long releaseTime;
}