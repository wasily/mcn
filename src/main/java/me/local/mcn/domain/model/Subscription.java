package me.local.mcn.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Document(collection = "subscriptions")
public class Subscription {
    @Field(value = "title")
    private String title;

    @Field(value = "email")
    private String email;

    @Field(value = "lastUpdateTime")
    @JsonIgnore
    private LocalDateTime lastUpdateTime;
}
