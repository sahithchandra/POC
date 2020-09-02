package com.wish.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * The Class Message.
 */
@Component

/**
 * Instantiates a new message.
 */
@Data
public class Message {

	/** The name. */
	private String name;

	/** The month. */
	private Integer month;

	/** The day. */
	private Integer day;

	/** The year. */
	private Integer year;

	/** The hour. */
	private Integer hour;

	/** The minutes. */
	private Integer minutes;

	/** The message. */
	private String message;

}
