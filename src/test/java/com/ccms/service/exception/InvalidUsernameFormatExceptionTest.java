package com.ccms.service.exception;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvalidUsernameFormatExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Arrange
        String expectedMessage = "Username contains illegal characters";

        // Act
        InvalidUsernameFormatException exception = new InvalidUsernameFormatException(expectedMessage);

        // Assert
        // Check that the exception message matches the expected message
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the provided message.");
    }
}
