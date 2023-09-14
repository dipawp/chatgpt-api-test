package com.dipawp.chatgpt.api;

import lombok.Data;

@Data
public class ChatCompletionUsage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;

}
