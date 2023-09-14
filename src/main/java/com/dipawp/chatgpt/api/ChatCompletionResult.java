package com.dipawp.chatgpt.api;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ChatCompletionResult {
    private String id;
    public int created;
    private String model;
    private ChatCompletionUsage usage;
    private ArrayList<ChatCompletionChoice> choices;

}
