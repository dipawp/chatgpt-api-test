package com.dipawp.chatgpt.api;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatCompletionRequest {
	
	private String model;
	private List<ChatCompletionMessage> messages;
	private double temperature;
	//private double top_n;
	private int n;
	private boolean stream;
	private int max_tokens;
	private String user;
	

}
