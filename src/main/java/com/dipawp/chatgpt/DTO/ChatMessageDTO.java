package com.dipawp.chatgpt.DTO;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class ChatMessageDTO {
	private String role;
	private String content;
	

}
