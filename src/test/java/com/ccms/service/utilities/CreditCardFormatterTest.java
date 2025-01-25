package com.ccms.service.utilities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CreditCardFormatter.
 */
public class CreditCardFormatterTest {

    private final CreditCardFormatter creditCardFormatter = new CreditCardFormatter();

    // Test the maskCreditCardNumber method
    @Test
    public void testMaskCreditCardNumber_validInput() {
        String creditCardNumber = "1234567812345678";

        String maskedNumber = creditCardFormatter.maskCreditCardNumber(creditCardNumber);

        // Check if the first 12 digits are masked with asterisks and the last 4 are intact
        assertEquals("****-****-****-5678", maskedNumber);
    }

    @Test
    public void testMaskCreditCardNumber_invalidLength() {
        String invalidCardNumber = "12345678";  // Invalid length (less than 16 digits)

        // Verify that an exception is thrown when the card number is invalid
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            creditCardFormatter.maskCreditCardNumber(invalidCardNumber);
        });

        assertEquals("Invalid credit card number length.", exception.getMessage());
    }

    @Test
    public void testMaskCreditCardNumber_withNonNumericCharacters() {
        String cardWithSpaces = "1234 5678 1234 5678";

        String maskedNumber = creditCardFormatter.maskCreditCardNumber(cardWithSpaces);

        assertEquals("****-****-****-5678", maskedNumber);
    }

    // Test the unmaskCreditCardNumber method
    @Test
    public void testUnmaskCreditCardNumber_validInput() {
        String maskedCardNumber = "1234567812345678";

        String unmaskedNumber = creditCardFormatter.unmaskCreditCardNumber(maskedCardNumber);

        // Check if the full credit card number is restored with dashes
        assertEquals("1234-5678-1234-5678", unmaskedNumber);
    }

    @Test
    public void testUnmaskCreditCardNumber_invalidLength() {
        String invalidMaskedCardNumber = "****-****-****-567";  // Invalid length (less than 16 digits)

        // Verify that an exception is thrown when the card number is invalid
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            creditCardFormatter.unmaskCreditCardNumber(invalidMaskedCardNumber);
        });

        assertEquals("Invalid masked card number length.", exception.getMessage());
    }

}
