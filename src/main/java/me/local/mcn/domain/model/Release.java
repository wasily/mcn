package me.local.mcn.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "releases")
public class Release {
    @Field(value = "title")
    private String title;

    @Field(value = "size")
    private long size;

    @Field(value = "infoHash")
    private String infoHash;

    @Field(value = "releaseTime")
    private LocalDateTime releaseTime;
}
