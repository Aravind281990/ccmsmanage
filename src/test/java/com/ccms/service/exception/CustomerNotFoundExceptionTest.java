package com.ccms.service.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerNotFoundExceptionTest {

    @Test
    void testConstructorWithUsername() {
        // Arrange
        String username = "john_doe";

        // Act
        CustomerNotFoundException exception = new CustomerNotFoundException(username);

        // Assert
        // Check that the exception message contains the correct username
        assertEquals("User not found: " + username, exception.getMessage(), "The exception message should include the correct username.");
    }
}
