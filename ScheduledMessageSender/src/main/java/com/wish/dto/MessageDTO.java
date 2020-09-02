package com.wish.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * The Class MessageDTO.
 */
@Component

/**
 * Instantiates a new message DTO.
 */
@Data
public class MessageDTO {

	/** The messages. */
	private List<Message> messages;

}
