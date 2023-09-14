package com.dipawp.chatgpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dipawp.chatgpt.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{

}
