package com.dipawp.chatgpt.api;

import lombok.Data;

@Data
public class ChatCompletionChoice {
	private ChatCompletionMessage message;
	 private String finish_reason;
	 private int index;
}
