package com.ccms.service.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardNotFoundExceptionTest {

    @Test
    void testConstructorWithUsername() {
        // Arrange
        String username = "john_doe";

        // Act
        CreditCardNotFoundException exception = new CreditCardNotFoundException(username);

        // Assert
        // Check that the message contains the correct username
        assertEquals("No credit card found for username: " + username, exception.getMessage(), "The exception message should include the correct username.");
    }

    @Test
    void testConstructorWithMessageAndCause() {
        // Arrange
        String message = "Custom error message";
        Throwable cause = new Throwable("Cause of the exception");

        // Act
        CreditCardNotFoundException exception = new CreditCardNotFoundException(message, cause);

        // Assert
        // Check that the message is set correctly
        assertEquals(message, exception.getMessage(), "The exception message should match the provided custom message.");

        // Check that the cause is set correctly
        assertEquals(cause, exception.getCause(), "The cause should be correctly set.");
    }
}
