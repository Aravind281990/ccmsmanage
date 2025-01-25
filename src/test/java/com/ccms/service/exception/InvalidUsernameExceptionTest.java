package com.ccms.service.exception;

import com.ccms.service.exception.InvalidUsernameException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvalidUsernameExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Arrange
        String expectedMessage = "Invalid username format";

        // Act
        InvalidUsernameException exception = new InvalidUsernameException(expectedMessage);

        // Assert
        // Check that the exception message matches the expected message
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the provided message.");
    }
}
