package com.ccms.service.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardProcessingExceptionTest {

    @Test
    void testConstructorWithMessageAndCause() {
        // Arrange
        String expectedMessage = "Error processing credit card";
        Throwable cause = new Throwable("Underlying cause");

        // Act
        CreditCardProcessingException exception = new CreditCardProcessingException(expectedMessage, cause);

        // Assert
        // Check that the exception message is correctly set
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the provided message.");

        // Check that the cause is correctly set
        assertEquals(cause, exception.getCause(), "The cause should be correctly set.");
    }
}
