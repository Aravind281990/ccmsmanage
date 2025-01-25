package com.ccms.service.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransactionNotFoundExceptionTest {

    @Test
    void testConstructorWithUsername() {
        // Arrange
        String username = "john_doe";
        String expectedMessage = "No credit card transactions found for username: " + username;

        // Act
        TransactionNotFoundException exception = new TransactionNotFoundException(username);

        // Assert
        // Verify that the exception message contains the expected message
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should include the username.");
    }
}
