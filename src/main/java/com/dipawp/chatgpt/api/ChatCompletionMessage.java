package com.dipawp.chatgpt.api;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class ChatCompletionMessage {
	private ChatCompletionRole role;
    private String content;
    
}
