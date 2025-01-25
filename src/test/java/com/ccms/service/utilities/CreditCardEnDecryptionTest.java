package com.ccms.service.utilities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"aes.key=r92m3Pz0ijHXyepQSrZPxg=="}) // Use a valid Base64-encoded AES key
public class CreditCardEnDecryptionTest {

    @Autowired
    private CreditCardEnDecryption creditCardEnDecryption;

    private String creditCardNumber;

    @BeforeEach
    public void setUp() {
        // Initialize the credit card number for each test
        creditCardNumber = "1234567812345678";
    }

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        // Encrypt the credit card number
        String encrypted = creditCardEnDecryption.encrypt(creditCardNumber);
        System.out.println("Encrypted: " + encrypted);
        assertNotNull(encrypted, "Encrypted value should not be null");

        // Decrypt it back
        String decrypted = creditCardEnDecryption.decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);

        // Assert the decrypted value matches the original card number
        assertEquals(creditCardNumber, decrypted, "Decrypted value should match the original credit card number");
    }

    @Test
    public void testEncryptWithEmptyString() throws Exception {
        // Encrypt an empty string
        String encrypted = creditCardEnDecryption.encrypt("");
        System.out.println("Encrypted empty string: " + encrypted);
        assertNotNull(encrypted, "Encrypted empty string should not be null");

        // Decrypt it back
        String decrypted = creditCardEnDecryption.decrypt(encrypted);
        assertEquals("", decrypted, "Decrypted value of an empty string should be an empty string");
    }

    @Test
    public void testDecryptWithInvalidData() {
        // Test with invalid encrypted data
        String invalidData = "invalid_base64_data";

        // Try to decrypt invalid data and expect an exception
        assertThrows(Exception.class, () -> {
            creditCardEnDecryption.decrypt(invalidData);
        }, "Decryption should fail with invalid base64 data");
    }

    @Test
    public void testDecryptWithEmptyString() throws Exception {
        // Decrypt an empty encrypted string (use encrypted data from the earlier test)
        String encryptedEmpty = creditCardEnDecryption.encrypt("");
        String decrypted = creditCardEnDecryption.decrypt(encryptedEmpty);
        assertEquals("", decrypted, "Decrypted value of an empty string should be an empty string");
    }

    @Test
    public void testEncryptionAndDecryptionWithDifferentValues() throws Exception {
        String differentCreditCardNumber = "9876543210987654";
        
        // Encrypt the different card number
        String encrypted = creditCardEnDecryption.encrypt(differentCreditCardNumber);
        assertNotNull(encrypted, "Encrypted value should not be null");

        // Decrypt it back
        String decrypted = creditCardEnDecryption.decrypt(encrypted);
        
        // Assert that the decrypted value matches the original card number
        assertEquals(differentCreditCardNumber, decrypted, "Decrypted value should match the original different credit card number");
    }
}
