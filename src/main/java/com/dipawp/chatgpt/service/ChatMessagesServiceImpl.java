package com.dipawp.chatgpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dipawp.chatgpt.entity.ChatMessage;
import com.dipawp.chatgpt.repository.ChatMessageRepository;


@Service
public class ChatMessagesServiceImpl implements ChatMessageService{
	
	private final ChatMessageRepository chatMessageRepository;
	
	@Autowired
	public ChatMessagesServiceImpl(ChatMessageRepository chatMessageRepository) {
		this.chatMessageRepository = chatMessageRepository;
	}
	

	@Override
	public void addMessage(ChatMessage chatMessage) {
		// TODO Auto-generated method stub
		chatMessageRepository.save(chatMessage);
	}

	@Override
	public ChatMessage getMessagesById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChatMessage> getAllMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChatMessage> SearchMessagesByQ(String query) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
