package com.ccms.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ccms.service.controller.TransactionController;

/**
 * Service to consume access logs from a Kafka topic and process the messages.
 * <p>
 * This class listens to the "access-logs" Kafka topic. It is part of a Kafka
 * consumer group called "credit-card-consumer-group". Each time a message is
 * published to the topic, the {@link #listenTransactionLogs(String)} method is
 * triggered to process the log.
 * </p>
 */

@Service
public class AccessLogKafkaConsumer {
	
	
	private static final Logger logger = LoggerFactory.getLogger(AccessLogKafkaConsumer.class);

	/**
	 * Kafka listener method that listens to messages on the "access-logs" topic.
	 * The method is automatically triggered when a message is received on the
	 * topic.
	 * <p>
	 * In this simple implementation, the message (log data) is just printed to the
	 * console. In a real-world scenario, you could process this message (e.g.,
	 * storing logs in a database, triggering alerts, or monitoring activities).
	 * </p>
	 *
	 * @param message The message received from the Kafka topic. This could be the
	 *                log details regarding user access events or other system
	 *                events.
	 */

	@KafkaListener(topics = "access-logs", groupId = "credit-card-consumer-group")
	public void listenTransactionLogs(String message) {

		// Log the received message to the console (can be replaced with actual
		// processing logic)

		logger.info("Received access logs: " + message);
	}
}
