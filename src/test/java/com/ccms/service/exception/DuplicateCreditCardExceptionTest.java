package com.ccms.service.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DuplicateCreditCardExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Arrange
        String expectedMessage = "Duplicate credit card entry detected";

        // Act
        DuplicateCreditCardException exception = new DuplicateCreditCardException(expectedMessage);

        // Assert
        // Check that the exception message matches the expected message
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the provided message.");
    }
}
