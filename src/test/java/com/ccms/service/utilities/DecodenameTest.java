package com.ccms.service.utilities;

import com.ccms.service.exception.InvalidUsernameException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;

/**
 * Test class for Decodename.
 */
public class DecodenameTest {

    private final Decodename decodename = new Decodename();

    // Test valid Base64 encoded username
    @Test
    public void testDecodeUsername_validInput() {
        String encodedUsername = Base64.getEncoder().encodeToString("validUsername".getBytes());

        String decodedUsername = decodename.decodeUsername(encodedUsername);

        assertEquals("validUsername", decodedUsername);
    }

    // Test empty username (should throw InvalidUsernameException)
    @Test
    public void testDecodeUsername_emptyUsername() {
        String emptyUsername = "";

        InvalidUsernameException exception = assertThrows(InvalidUsernameException.class, () -> {
            decodename.decodeUsername(emptyUsername);
        });

        assertEquals("Username cannot be null or empty", exception.getMessage());
    }

    // Test null username (should throw InvalidUsernameException)
    @Test
    public void testDecodeUsername_nullUsername() {
        String nullUsername = null;

        InvalidUsernameException exception = assertThrows(InvalidUsernameException.class, () -> {
            decodename.decodeUsername(nullUsername);
        });

        assertEquals("Username cannot be null or empty", exception.getMessage());
    }

    // Test username exceeding maximum length (should throw InvalidUsernameException)
    @Test
    public void testDecodeUsername_exceedingMaxLength() {
        String longUsername = Base64.getEncoder().encodeToString("ThisIsAVeryLongUsernameExceedingLimit".getBytes());

        InvalidUsernameException exception = assertThrows(InvalidUsernameException.class, () -> {
            decodename.decodeUsername(longUsername);
        });

        assertEquals("Username must not exceed 25 characters", exception.getMessage());
    }

    // Test invalid Base64 encoding (should throw InvalidUsernameException)
    @Test
    public void testDecodeUsername_invalidBase64() {
        String invalidBase64 = "invalidBase64!";

        InvalidUsernameException exception = assertThrows(InvalidUsernameException.class, () -> {
            decodename.decodeUsername(invalidBase64);
        });

        assertEquals("Invalid Base64 encoding for username", exception.getMessage());
    }

    // Test the padding correction in the Base64 string
    @Test
    public void testEnsureBase64Padding() {
        String base64WithoutPadding = "dGVzdHVzZXI"; // Missing padding

        // The encoded string "dGVzdHVzZXI" represents "testuser" without padding
        String correctedBase64 = decodename.ensureBase64Padding(base64WithoutPadding);

        assertEquals("dGVzdHVzZXI=", correctedBase64);  // Padding should be added to make it valid
    }

    @Test
    public void testEnsureBase64Padding_validInput() {
        String base64WithValidPadding = "dGVzdHVzZXI="; // Correct padding

        String correctedBase64 = decodename.ensureBase64Padding(base64WithValidPadding);

        assertEquals("dGVzdHVzZXI=", correctedBase64);  // No change expected
    }

    @Test
    public void testEnsureBase64Padding_fullPadding() {
        String base64WithFullPadding = "dGVzdHVzZXI=="; // Already full padding

        String correctedBase64 = decodename.ensureBase64Padding(base64WithFullPadding);

        assertEquals("dGVzdHVzZXI==", correctedBase64);  // No change expected
    }
}
