package com.ccms.service.model;

import com.ccms.service.model.Transaction.TransactionDetail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionWithCreditCardInfoTest {

    // Test 1: Constructor Test
    @Test
    void testConstructor() {
        // Create a sample TransactionDetail
        TransactionDetail transactionDetail = new TransactionDetail(
                12345L,               // transactionId
                "2025-01-01",         // transactionDate
                "12:00:00",           // transactionTime
                "db",                 // transactionType (debit)
                100.0,                // transactionAmount
                "Purchase at Store"   // transactionDesc
        );

        // Create TransactionWithCreditCardInfo object using the constructor
        TransactionWithCreditCardInfo transactionWithCreditCardInfo = new TransactionWithCreditCardInfo(
                "1234-5678-9876-5432", transactionDetail);

        // Assert that the constructor sets the values correctly
        assertEquals("1234-5678-9876-5432", transactionWithCreditCardInfo.getCreditCardNumber());
        assertNotNull(transactionWithCreditCardInfo.getTransactionDetail());
        assertEquals(12345L, transactionWithCreditCardInfo.getTransactionDetail().getTransactionId());
        assertEquals("2025-01-01", transactionWithCreditCardInfo.getTransactionDetail().getTransactionDate());
        assertEquals(100.0, transactionWithCreditCardInfo.getTransactionDetail().getTransactionAmount());
    }

    // Test 2: Getters and Setters Test
    @Test
    void testGettersAndSetters() {
        // Create a sample TransactionDetail
        TransactionDetail transactionDetail = new TransactionDetail(
                12345L,               // transactionId
                "2025-01-01",         // transactionDate
                "12:00:00",           // transactionTime
                "db",                 // transactionType (debit)
                100.0,                // transactionAmount
                "Purchase at Store"   // transactionDesc
        );

        // Create TransactionWithCreditCardInfo object using the constructor
        TransactionWithCreditCardInfo transactionWithCreditCardInfo = new TransactionWithCreditCardInfo(
                "1234-5678-9876-5432", transactionDetail);

        // Test Getters
        assertEquals("1234-5678-9876-5432", transactionWithCreditCardInfo.getCreditCardNumber());
        assertEquals(transactionDetail, transactionWithCreditCardInfo.getTransactionDetail());

        // Test Setters
        TransactionDetail newTransactionDetail = new TransactionDetail(
                67890L,               // transactionId
                "2025-02-01",         // transactionDate
                "14:00:00",           // transactionTime
                "cr",                 // transactionType (credit)
                200.0,                // transactionAmount
                "Refund from Store"   // transactionDesc
        );

        transactionWithCreditCardInfo.setCreditCardNumber("9876-5432-1234-5678");
        transactionWithCreditCardInfo.setTransactionDetail(newTransactionDetail);

        // Verify the setters
        assertEquals("9876-5432-1234-5678", transactionWithCreditCardInfo.getCreditCardNumber());
        assertEquals(newTransactionDetail, transactionWithCreditCardInfo.getTransactionDetail());
    }

    // Test 3: TransactionDetail Object Test
    @Test
    void testTransactionDetailObject() {
        // Create a sample TransactionDetail
        TransactionDetail transactionDetail = new TransactionDetail(
                12345L,               // transactionId
                "2025-01-01",         // transactionDate
                "12:00:00",           // transactionTime
                "db",                 // transactionType (debit)
                100.0,                // transactionAmount
                "Purchase at Store"   // transactionDesc
        );

        // Verify the details of TransactionDetail
        assertEquals(12345L, transactionDetail.getTransactionId());
        assertEquals("2025-01-01", transactionDetail.getTransactionDate());
        assertEquals("12:00:00", transactionDetail.getTransactionTime());
        assertEquals("db", transactionDetail.getTransactionType());
        assertEquals(100.0, transactionDetail.getTransactionAmount());
        assertEquals("Purchase at Store", transactionDetail.getTransactionDesc());
    }

    // Test 4: Null Value Test for TransactionDetail (Null safety check)
    @Test
    void testNullTransactionDetail() {
        // Create TransactionWithCreditCardInfo with null transactionDetail
        TransactionWithCreditCardInfo transactionWithCreditCardInfo = new TransactionWithCreditCardInfo(
                "1234-5678-9876-5432", null);

        // Assert the creditCardNumber is set, but transactionDetail is null
        assertEquals("1234-5678-9876-5432", transactionWithCreditCardInfo.getCreditCardNumber());
        assertNull(transactionWithCreditCardInfo.getTransactionDetail());
    }

    // Test 5: Null Value Test for CreditCardNumber (Null safety check)
    @Test
    void testNullCreditCardNumber() {
        // Create a sample TransactionDetail
        TransactionDetail transactionDetail = new TransactionDetail(
                12345L,               // transactionId
                "2025-01-01",         // transactionDate
                "12:00:00",           // transactionTime
                "db",                 // transactionType (debit)
                100.0,                // transactionAmount
                "Purchase at Store"   // transactionDesc
        );

        // Create TransactionWithCreditCardInfo with null creditCardNumber
        TransactionWithCreditCardInfo transactionWithCreditCardInfo = new TransactionWithCreditCardInfo(
                null, transactionDetail);

        // Assert that the creditCardNumber is null and transactionDetail is not null
        assertNull(transactionWithCreditCardInfo.getCreditCardNumber());
        assertNotNull(transactionWithCreditCardInfo.getTransactionDetail());
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        TransactionDetail transactionDetail1 = new TransactionDetail(
                1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment"
        );
        TransactionDetail transactionDetail2 = new TransactionDetail(
                1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment"
        );

        TransactionWithCreditCardInfo tx1 = new TransactionWithCreditCardInfo("1234567812345678", transactionDetail1);
        TransactionWithCreditCardInfo tx2 = new TransactionWithCreditCardInfo("1234567812345678", transactionDetail2);

        // Act & Assert
        assertEquals(tx1, tx2, "The TransactionWithCreditCardInfo objects should be equal when creditCardNumber and transactionDetail are the same");
        assertEquals(tx1.hashCode(), tx2.hashCode(), "The hash codes should be equal when the objects are equal");

        // Modify one field to make the objects unequal
        tx2.setCreditCardNumber("8765432187654321");

        assertNotEquals(tx1, tx2, "The TransactionWithCreditCardInfo objects should not be equal when creditCardNumber is different");
        assertNotEquals(tx1.hashCode(), tx2.hashCode(), "The hash codes should be different when the objects are unequal");
    }
    
    
    @Test
    void testToString() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail(
                1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment"
        );
        TransactionWithCreditCardInfo tx = new TransactionWithCreditCardInfo("1234567812345678", transactionDetail);

        // Act
        String result = tx.toString();

        // Assert
        assertTrue(result.contains("creditCardNumber=1234567812345678"), "The toString() method should include the credit card number");     
    }
    
    @Test
    void testDefaultConstructor() {
        // Act
        TransactionWithCreditCardInfo tx = new TransactionWithCreditCardInfo();

        // Assert
        assertNull(tx.getCreditCardNumber(), "The creditCardNumber should be null after using the default constructor");
        assertNull(tx.getTransactionDetail(), "The transactionDetail should be null after using the default constructor");
    }

    
    @Test
    void testCanEqual() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail(
                1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment"
        );
        TransactionWithCreditCardInfo tx = new TransactionWithCreditCardInfo("1234567812345678", transactionDetail);

        // Act & Assert
        assertTrue(tx.canEqual(tx), "canEqual should return true for objects of the same type");
        assertFalse(tx.canEqual(new Object()), "canEqual should return false for objects of different types");
    }
    
    
    @Test
    void testEqualsWithNull() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail(
                1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment"
        );
        TransactionWithCreditCardInfo tx = new TransactionWithCreditCardInfo("1234567812345678", transactionDetail);

        // Act & Assert
        assertNotEquals(tx, null, "The object should not be equal to null");
    }


    
    
}
