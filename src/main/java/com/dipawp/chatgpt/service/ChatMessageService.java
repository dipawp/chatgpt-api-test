package com.dipawp.chatgpt.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dipawp.chatgpt.entity.ChatMessage;

public interface ChatMessageService {
	
	public void addMessage(ChatMessage chatMessage);
	public ChatMessage getMessagesById(long id);
	public List<ChatMessage> getAllMessages();
	
	@Query("Select c from ChatMessages c where c.content like %:query%")
	public List<ChatMessage> SearchMessagesByQ(@Param("query") String query);

}
