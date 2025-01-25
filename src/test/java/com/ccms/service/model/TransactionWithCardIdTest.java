package com.ccms.service.model;

import com.ccms.service.model.Transaction.TransactionDetail;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransactionWithCardIdTest {

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

        // Create TransactionWithCardId object using the constructor
        TransactionWithCardId transactionWithCardId = new TransactionWithCardId(101, transactionDetail);

        // Assert that the constructor sets the values correctly
        assertEquals(101, transactionWithCardId.getCreditCardId());
        assertNotNull(transactionWithCardId.getTransactionDetail());
        assertEquals(12345L, transactionWithCardId.getTransactionDetail().getTransactionId());
        assertEquals("2025-01-01", transactionWithCardId.getTransactionDetail().getTransactionDate());
        assertEquals(100.0, transactionWithCardId.getTransactionDetail().getTransactionAmount());
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

        // Create TransactionWithCardId object using the constructor
        TransactionWithCardId transactionWithCardId = new TransactionWithCardId(101, transactionDetail);

        // Test Getters
        assertEquals(101, transactionWithCardId.getCreditCardId());
        assertEquals(transactionDetail, transactionWithCardId.getTransactionDetail());

        // Test Setters
        TransactionDetail newTransactionDetail = new TransactionDetail(
                67890L,               // transactionId
                "2025-02-01",         // transactionDate
                "14:00:00",           // transactionTime
                "cr",                 // transactionType (credit)
                200.0,                // transactionAmount
                "Refund from Store"   // transactionDesc
        );

        transactionWithCardId.setCreditCardId(102);
        transactionWithCardId.setTransactionDetail(newTransactionDetail);

        // Verify the setters
        assertEquals(102, transactionWithCardId.getCreditCardId());
        assertEquals(newTransactionDetail, transactionWithCardId.getTransactionDetail());
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
        // Create TransactionWithCardId with null transactionDetail
        TransactionWithCardId transactionWithCardId = new TransactionWithCardId(101, null);

        // Assert the creditCardId is set but transactionDetail is null
        assertEquals(101, transactionWithCardId.getCreditCardId());
        assertNull(transactionWithCardId.getTransactionDetail());
    }

    // Test 5: Null Value Test for CreditCardId (Null safety check)
    @Test
    void testNullCreditCardId() {
        // Create a sample TransactionDetail
        TransactionDetail transactionDetail = new TransactionDetail(
                12345L,               // transactionId
                "2025-01-01",         // transactionDate
                "12:00:00",           // transactionTime
                "db",                 // transactionType (debit)
                100.0,                // transactionAmount
                "Purchase at Store"   // transactionDesc
        );

        // Create TransactionWithCardId with null creditCardId
        TransactionWithCardId transactionWithCardId = new TransactionWithCardId(0, transactionDetail);

        // Assert that the creditCardId is set to 0, and transactionDetail is not null
        assertEquals(0, transactionWithCardId.getCreditCardId());
        assertNotNull(transactionWithCardId.getTransactionDetail());
    }
}
