package com.ccms.service.utilities;

import com.ccms.service.kafka.CreditCardKafkaProducer;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CreditCardLogUtil.
 */
public class CreditCardLogUtilTest {

    // Mock the Kafka producer
    @Mock
    private CreditCardKafkaProducer mockKafkaProducer;

    // Real instance of CreditCardLogUtil, since it's a utility class
    private CreditCardLogUtil creditCardLogUtil;

    @BeforeEach
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Initialize the utility class
        creditCardLogUtil = new CreditCardLogUtil();
    }

    @Test
    public void testLogCreditCard_logsCorrectly() {
        // Prepare test data
        Map<String, Object> jsonLogMap = new HashMap<>();
        String status = "SUCCESS";
        String message = "Credit card payment successful";
        String username = "testUser";
        int creditCardId = 123456;

        // Call the method to log
        creditCardLogUtil.logCreditCard(jsonLogMap, status, message, username, creditCardId, mockKafkaProducer);

        // Verify that the JSON map is populated correctly
        assertEquals("testUser", jsonLogMap.get("customer_name"));
        assertEquals(123456, jsonLogMap.get("credit_card_id"));
        assertEquals("Credit card payment successful", jsonLogMap.get("message"));
        assertEquals("SUCCESS", jsonLogMap.get("status"));

        // Verify that the timestamp is correctly added and is in the expected format
        assertNotNull(jsonLogMap.get("timestamp"));
        assertTrue(Instant.parse(jsonLogMap.get("timestamp").toString()).isBefore(Instant.now()));

        // Verify interaction with Kafka producer (the method should be called once with the correct message)
        String expectedJsonLog = new JSONObject(jsonLogMap).toString();
        verify(mockKafkaProducer, times(1)).sendCreditCardLog(expectedJsonLog);
    }

    @Test
    public void testLogCreditCard_withEmptyUsername() {
        // Prepare test data with empty username
        Map<String, Object> jsonLogMap = new HashMap<>();
        String status = "FAILURE";
        String message = "Credit card payment failed";
        String username = "";  // Empty username
        int creditCardId = 123456;

        // Call the method to log
        creditCardLogUtil.logCreditCard(jsonLogMap, status, message, username, creditCardId, mockKafkaProducer);

        // Verify that the username is logged as empty (edge case)
        assertEquals("", jsonLogMap.get("customer_name"));
    }

    @Test
    public void testLogCreditCard_withNullUsername() {
        // Prepare test data with null username
        Map<String, Object> jsonLogMap = new HashMap<>();
        String status = "FAILURE";
        String message = "Credit card payment failed";
        String username = null;  // Null username
        int creditCardId = 123456;

        // Call the method to log
        creditCardLogUtil.logCreditCard(jsonLogMap, status, message, username, creditCardId, mockKafkaProducer);

        // Verify that the username is logged as null (edge case)
        assertNull(jsonLogMap.get("customer_name"));
    }

    @Test
    public void testLogCreditCard_sendsCorrectLogToKafka() {
        // Prepare test data
        Map<String, Object> jsonLogMap = new HashMap<>();
        String status = "SUCCESS";
        String message = "Credit card payment successful";
        String username = "testUser";
        int creditCardId = 123456;

        // Call the method to log
        creditCardLogUtil.logCreditCard(jsonLogMap, status, message, username, creditCardId, mockKafkaProducer);

        // Prepare expected JSON string from map
        String expectedJsonLog = new JSONObject(jsonLogMap).toString();

        // Verify that the sendCreditCardLog method is called once with the correct argument
        verify(mockKafkaProducer, times(1)).sendCreditCardLog(expectedJsonLog);
    }
}
