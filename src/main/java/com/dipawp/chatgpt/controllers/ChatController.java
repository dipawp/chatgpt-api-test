package com.dipawp.chatgpt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.dipawp.chatgpt.entity.User;
import com.dipawp.chatgpt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dipawp.chatgpt.DTO.ChatMessageDTO;
import com.dipawp.chatgpt.api.ChatCompletionMessage;
import com.dipawp.chatgpt.api.ChatCompletionRequest;
import com.dipawp.chatgpt.api.ChatCompletionResult;
import com.dipawp.chatgpt.api.ChatCompletionRole;
import com.dipawp.chatgpt.entity.ChatMessage;
import com.dipawp.chatgpt.service.ChatMessagesServiceImpl;
import com.dipawp.chatgpt.service.OAIService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ChatController {
	
	ChatMessagesServiceImpl chatMessagesServiceImpl;
	UserRepository userRepository;
	OAIService oaiService;
	List<ChatCompletionMessage> listChat = new ArrayList<>();
	List<ChatMessageDTO> chatMessageDTOs = new ArrayList<>();
	
	
	@Autowired
	public ChatController(ChatMessagesServiceImpl chatMessagesServiceImpl, OAIService oaiService, UserRepository userRepository) {
		this.chatMessagesServiceImpl = chatMessagesServiceImpl;
		this.oaiService = oaiService;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/user")
	public String home() {
		if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken){
			return "views/login";
		}
		return "views/chat";
	}
	
	@PostMapping("/completion")
	@ResponseBody
	public String chatCompletionRequest(@RequestBody String msg, HttpServletRequest request) throws InterruptedException, ExecutionException {

		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User user = (userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

		for(ChatMessage chtm:user.getChatMessages()){
			chatMessageDTOs.add(ChatMessageDTO.builder()
					.role(chtm.getRole())
					.content(chtm.getContent())
					.build());
		}

		Gson gson = new Gson();

		ChatCompletionMessage message = gson.fromJson(msg, ChatCompletionMessage.class);
		message.setRole(ChatCompletionRole.user);
		
		chatMessageDTOs.add(ChatMessageDTO.builder()
				.role(message.getRole().name())
				.content(message.getContent())
				.build());
		listChat.add(message);
		chatMessagesServiceImpl.addMessage(ChatMessage.builder()
				.role(message.getRole().toString())
				.content(message.getContent())
				.user(user)
				.build());
		
		ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
				.model("gpt-3.5-turbo")
				.max_tokens(1000)
				.temperature(0.7)
				.n(1)
				.messages(listChat)
				.build();
		ChatCompletionMessage result = ChatCompletionMessage.builder()
				.content(oaiService.completeChat(chatCompletionRequest).get())
				.role(ChatCompletionRole.assistant)
				.build();
		ChatCompletionResult res = gson.fromJson(result.getContent(), ChatCompletionResult.class);
		if(res == null) {
			chatMessagesServiceImpl.addMessage(ChatMessage.builder()
					.role("assistant")
					.content("server error")
					.user(user)
					.build());
			return "Server error";
		}
		ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
				.role(res.getChoices().get(0).getMessage().getRole().name())
				.content(res.getChoices().get(0).getMessage().getContent())
				.build();
		chatMessageDTOs.add(chatMessageDTO);
		listChat.add(result);
		chatMessagesServiceImpl.addMessage(ChatMessage.builder()
				.role(chatMessageDTO.getRole())
				.content(chatMessageDTO.getContent())
				.user(user)
				.build());
		return chatMessageDTO.getContent();
	}

}
