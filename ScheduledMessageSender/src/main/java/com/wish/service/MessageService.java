package com.wish.service;

import com.wish.dto.MessageDTO;

/**
 * The Interface MessageService.
 */
public interface MessageService {

	/**
	 * Send message.
	 *
	 * @param messageDTO the message DTO
	 */
	public void sendMessage(MessageDTO messageDTO);

}
