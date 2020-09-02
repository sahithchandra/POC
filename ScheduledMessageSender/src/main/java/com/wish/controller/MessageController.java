package com.wish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wish.dto.MessageDTO;
import com.wish.service.MessageService;

/**
 * The Class MessageController.
 */
@RestController
public class MessageController {

	/** The message service. */
	@Autowired
	MessageService messageService;

	/**
	 * Send message.
	 *
	 * @param messageDTO the message DTO
	 */
	@PostMapping("/sendMessage")
	public void sendMessage(@RequestBody MessageDTO messageDTO) {
		messageService.sendMessage(messageDTO);
	}

}
