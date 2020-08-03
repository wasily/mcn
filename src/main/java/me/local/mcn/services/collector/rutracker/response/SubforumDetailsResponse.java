package me.local.mcn.services.collector.rutracker.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubforumDetailsResponse {
    @JsonProperty("result")
    HashMap<String, Long[]> result;
}
