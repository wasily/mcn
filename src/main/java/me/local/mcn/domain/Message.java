package me.local.mcn.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Message {
    private final String subject;
    private final String text;
}
