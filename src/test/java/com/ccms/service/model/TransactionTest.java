package com.ccms.service.model;

import org.junit.jupiter.api.Test;

import com.ccms.service.model.Transaction.TransactionDetail;
import com.ccms.service.model.Transaction.CreditCardTransaction;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

	
	   @Test
	    void testTransactionCreation() {
	        // Arrange
	        Transaction.TransactionDetail transactionDetail = new Transaction.TransactionDetail(
	                1001L, "2025-01-24", "12:30:00", "cr", 150.75, "Payment for Services"
	        );
	        
	        Transaction.CreditCardTransaction creditCardTransaction = new Transaction.CreditCardTransaction(
	                123456, Arrays.asList(transactionDetail)
	        );
	        
	        Transaction transaction = new Transaction(
	                "txn123", "john_doe", Arrays.asList(creditCardTransaction)
	        );

	        // Act and Assert
	        assertNotNull(transaction);
	        assertEquals("txn123", transaction.getId());
	        assertEquals("john_doe", transaction.getUsername());
	        assertEquals(1, transaction.getCreditcards().size());
	        assertEquals(123456, transaction.getCreditcards().get(0).getCreditCardId());
	        assertEquals(1, transaction.getCreditcards().get(0).getTransactions().size());
	        assertEquals(1001L, transaction.getCreditcards().get(0).getTransactions().get(0).getTransactionId());
	    }
	   
    @Test
    void testTransactionConstructorAndGettersSetters() {
        // Given
        Transaction.TransactionDetail transactionDetail = new Transaction.TransactionDetail(12345L, "2025-01-01", "12:00:00", "db", 100.0, "Purchase at Store");
        Transaction.CreditCardTransaction creditCardTransaction = new Transaction.CreditCardTransaction(1, List.of(transactionDetail));
        Transaction transaction = new Transaction("1", "john_doe", List.of(creditCardTransaction));

        // When
        String username = transaction.getUsername();
        double transactionAmount = transaction.getCreditcards().get(0).getTransactions().get(0).getTransactionAmount();
        String transactionDesc = transaction.getCreditcards().get(0).getTransactions().get(0).getTransactionDesc();

        // Then
        assertEquals("john_doe", username);
        assertEquals(100.0, transactionAmount);
        assertEquals("Purchase at Store", transactionDesc);
    }

    
    @Test
    void testTransactionDetailCreation() {
        // Arrange
        Transaction.TransactionDetail transactionDetail = new Transaction.TransactionDetail(
                1002L, "2025-01-24", "13:45:00", "db", 200.50, "Refund for purchase"
        );

        // Act and Assert
        assertNotNull(transactionDetail);
        assertEquals(1002L, transactionDetail.getTransactionId());
        assertEquals("2025-01-24", transactionDetail.getTransactionDate());
        assertEquals("13:45:00", transactionDetail.getTransactionTime());
        assertEquals("db", transactionDetail.getTransactionType());
        assertEquals(200.50, transactionDetail.getTransactionAmount());
        assertEquals("Refund for purchase", transactionDetail.getTransactionDesc());
    }

    @Test
    void testTransactionDefaultConstructor() {
        // Arrange
        Transaction transaction = new Transaction();

        // Act and Assert
        assertNotNull(transaction);
        assertNull(transaction.getId());
        assertNull(transaction.getUsername());
        assertNull(transaction.getCreditcards());
    }

    
    @Test
    void testCreditCardTransactionNestedClass() {
        // Arrange
        Transaction.TransactionDetail transactionDetail = new Transaction.TransactionDetail(
                1005L, "2025-01-27", "17:30:00", "db", 300.00, "Purchase"
        );
        Transaction.CreditCardTransaction creditCardTransaction = new Transaction.CreditCardTransaction(
                111222, Arrays.asList(transactionDetail)
        );
        Transaction transaction = new Transaction("txn126", "charlie_lee", Arrays.asList(creditCardTransaction));

        // Act and Assert
        assertEquals(111222, transaction.getCreditcards().get(0).getCreditCardId());
        assertEquals("Purchase", transaction.getCreditcards().get(0).getTransactions().get(0).getTransactionDesc());
    }

    
    @Test
    void testTransactionSettersAndGetters() {
        // Arrange
        Transaction.TransactionDetail transactionDetail = new Transaction.TransactionDetail(
                1003L, "2025-01-25", "14:00:00", "cr", 99.99, "Deposit"
        );
        Transaction.CreditCardTransaction creditCardTransaction = new Transaction.CreditCardTransaction(
                654321, Arrays.asList(transactionDetail)
        );
        Transaction transaction = new Transaction();

        // Act
        transaction.setId("txn124");
        transaction.setUsername("alice_smith");
        transaction.setCreditcards(Arrays.asList(creditCardTransaction));

        // Assert
        assertEquals("txn124", transaction.getId());
        assertEquals("alice_smith", transaction.getUsername());
        assertEquals(1, transaction.getCreditcards().size());
        assertEquals(654321, transaction.getCreditcards().get(0).getCreditCardId());
    }

    
    @Test
    void testCreditCardTransaction() {
        // Given
        Transaction.TransactionDetail transactionDetail = new Transaction.TransactionDetail(12345L, "2025-01-01", "12:00:00", "db", 100.0, "Purchase at Store");
        Transaction.CreditCardTransaction creditCardTransaction = new Transaction.CreditCardTransaction(1, List.of(transactionDetail));

        // When
        int creditCardId = creditCardTransaction.getCreditCardId();
        double amount = creditCardTransaction.getTransactions().get(0).getTransactionAmount();

        // Then
        assertEquals(1, creditCardId);
        assertEquals(100.0, amount);
    }

    @Test
    void testTransactionDetail() {
        // Given
        Transaction.TransactionDetail transactionDetail = new Transaction.TransactionDetail(12345L, "2025-01-01", "12:00:00", "db", 100.0, "Purchase at Store");

        // When
        String transactionType = transactionDetail.getTransactionType();
        double amount = transactionDetail.getTransactionAmount();

        // Then
        assertEquals("db", transactionType);
        assertEquals(100.0, amount);
    }
    
    
    
    @Test
    void testHashCode() {
        // Create two identical transactions
        Transaction.TransactionDetail detail1 = new Transaction.TransactionDetail(
            12345L, "2025-01-24", "14:30:00", "cr", 1500.00, "Payment Received"
        );
        Transaction.CreditCardTransaction creditCardTransaction1 = new Transaction.CreditCardTransaction(
            101, Arrays.asList(detail1)
        );
        Transaction transaction1 = new Transaction("user123", null, Arrays.asList(creditCardTransaction1));

        Transaction.TransactionDetail detail2 = new Transaction.TransactionDetail(
            12345L, "2025-01-24", "14:30:00", "cr", 1500.00, "Payment Received"
        );
        Transaction.CreditCardTransaction creditCardTransaction2 = new Transaction.CreditCardTransaction(
            101, Arrays.asList(detail2)
        );
        Transaction transaction2 = new Transaction("user123", null, Arrays.asList(creditCardTransaction2));

        // Assert that the hash codes of two equal transactions are the same
        assertEquals(transaction1.hashCode(), transaction2.hashCode());

        // Modify the second transaction's username
        transaction2.setUsername("user124");

        // Assert that the hash codes are now different
        assertNotEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    
    @Test
    void testEqualsAndHashCode1() {
        // Arrange
        Transaction.TransactionDetail transactionDetail1 = new Transaction.TransactionDetail(1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment");
        Transaction.TransactionDetail transactionDetail2 = new Transaction.TransactionDetail(1002L, "2025-01-26", "11:00:00", "db", 50.25, "Refund");

        Transaction.CreditCardTransaction creditCardTransaction1 = new Transaction.CreditCardTransaction(123, Arrays.asList(transactionDetail1, transactionDetail2));
        Transaction.CreditCardTransaction creditCardTransaction2 = new Transaction.CreditCardTransaction(123, Arrays.asList(transactionDetail1, transactionDetail2));

        Transaction transaction1 = new Transaction("1", "user1", Arrays.asList(creditCardTransaction1));
        Transaction transaction2 = new Transaction("1", "user1", Arrays.asList(creditCardTransaction2));

        // Act & Assert
        assertEquals(transaction1, transaction2, "Transaction objects with the same fields should be equal");
        assertEquals(transaction1.hashCode(), transaction2.hashCode(), "The hash codes should be the same when the objects are equal");

        // Modify a field and check inequality
        transaction2.setUsername("user2");
        assertNotEquals(transaction1, transaction2, "The Transaction objects should not be equal when 'username' is different");
        assertNotEquals(transaction1.hashCode(), transaction2.hashCode(), "The hash codes should differ when the objects are unequal");
    }
    
//    @Test
//    void testToString1() {
//        // Arrange
//        Transaction.TransactionDetail transactionDetail = new Transaction.TransactionDetail(1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment");
//        Transaction.CreditCardTransaction creditCardTransaction = new Transaction.CreditCardTransaction(123, Arrays.asList(transactionDetail));
//        Transaction transaction = new Transaction("1", "user1", Arrays.asList(creditCardTransaction));
//
//        // Act
//        String result = transaction.toString();
//
//        // Assert
//        assertTrue(result.contains("creditcards=[CreditCardTransaction{creditCardId=123"), "The toString() method should include credit card transaction details");
//        assertTrue(result.contains("TransactionDetail{transactionId=1001"), "The toString() method should include transaction details");
//    }

    @Test
    void testSetIdAndSetUsername() {
        // Create an empty Transaction object
        Transaction transaction = new Transaction();

        // Set the ID and username
        transaction.setId("transaction123");
        transaction.setUsername("user123");

        // Assertions to check if the values were set correctly
        assertEquals("transaction123", transaction.getId());
        assertEquals("user123", transaction.getUsername());
    }

//    @Test
//    void testTransactionConstructor() {
//        // Create a Transaction object using the no-argument constructor
//        Transaction transaction = new Transaction();
//
//        // Assert that the object is instantiated and has default values (null or empty lists)
//        assertNotNull(transaction);
//        assertNull(transaction.getId());
//        assertNull(transaction.getUsername());
//        assertTrue(transaction.getCreditcards().isEmpty());
//    }

    @Test
    void testCanEqual1() {
        // Create two transaction objects
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();

        // Assert that canEqual returns true for objects of the same class
        assertTrue(transaction1.canEqual(transaction2));

        // Assert that canEqual returns false for objects of different classes
        String otherObject = "Some String";
        assertFalse(transaction1.canEqual(otherObject));
        
    }

    

    @Test
    void testSetTransactionId() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail();

        // Act
        transactionDetail.setTransactionId(12345L);

        // Assert
        assertEquals(12345L, transactionDetail.getTransactionId(), "The transactionId should be set correctly");
    }

    @Test
    void testSetTransactionDate() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail();

        // Act
        transactionDetail.setTransactionDate("2025-01-25");

        // Assert
        assertEquals("2025-01-25", transactionDetail.getTransactionDate(), "The transactionDate should be set correctly");
    }

    @Test
    void testSetTransactionTime() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail();

        // Act
        transactionDetail.setTransactionTime("10:00:00");

        // Assert
        assertEquals("10:00:00", transactionDetail.getTransactionTime(), "The transactionTime should be set correctly");
    }

    @Test
    void testSetTransactionType() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail();

        // Act
        transactionDetail.setTransactionType("cr");

        // Assert
        assertEquals("cr", transactionDetail.getTransactionType(), "The transactionType should be set correctly");
    }

    @Test
    void testSetTransactionAmount() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail();

        // Act
        transactionDetail.setTransactionAmount(100.50);

        // Assert
        assertEquals(100.50, transactionDetail.getTransactionAmount(), 0.001, "The transactionAmount should be set correctly");
    }

    @Test
    void testSetTransactionDesc() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail();

        // Act
        transactionDetail.setTransactionDesc("Payment for services");

        // Assert
        assertEquals("Payment for services", transactionDetail.getTransactionDesc(), "The transactionDesc should be set correctly");
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        TransactionDetail transactionDetail1 = new TransactionDetail(1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment");
        TransactionDetail transactionDetail2 = new TransactionDetail(1002L, "2025-01-26", "11:00:00", "db", 50.25, "Refund");

        CreditCardTransaction tx1 = new CreditCardTransaction(123, Arrays.asList(transactionDetail1, transactionDetail2));
        CreditCardTransaction tx2 = new CreditCardTransaction(123, Arrays.asList(transactionDetail1, transactionDetail2));

        // Act & Assert
        assertEquals(tx1, tx2, "The CreditCardTransaction objects should be equal if creditCardId and transactions are the same");
        assertEquals(tx1.hashCode(), tx2.hashCode(), "The hash codes should be the same when the objects are equal");

        // Modify a field to ensure inequality
        tx2.setCreditCardId(456);
        assertNotEquals(tx1, tx2, "The CreditCardTransaction objects should not be equal when creditCardId is different");
        assertNotEquals(tx1.hashCode(), tx2.hashCode(), "The hash codes should differ when the objects are unequal");
    }
    
    
    @Test
    void testToString() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail(1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment");
        CreditCardTransaction tx = new CreditCardTransaction(123, Arrays.asList(transactionDetail));

        // Act
        String result = tx.toString();

        // Assert
        assertTrue(result.contains("creditCardId=123"), "The toString() method should include the creditCardId");
        //assertTrue(result.contains("transactions=[TransactionDetail(transactionId=1001"), "The toString() method should include transaction details");
    }

    
    @Test
    void testSetCreditCardId() {
        // Arrange
        CreditCardTransaction tx = new CreditCardTransaction();

        // Act
        tx.setCreditCardId(456);

        // Assert
        assertEquals(456, tx.getCreditCardId(), "The creditCardId should be set correctly");
    }

    
    
    @Test
    void testSetTransactions() {
        // Arrange
        TransactionDetail transactionDetail1 = new TransactionDetail(1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment");
        TransactionDetail transactionDetail2 = new TransactionDetail(1002L, "2025-01-26", "11:00:00", "db", 50.25, "Refund");

        CreditCardTransaction tx = new CreditCardTransaction();

        // Act
        tx.setTransactions(Arrays.asList(transactionDetail1, transactionDetail2));

        // Assert
        assertEquals(2, tx.getTransactions().size(), "The transactions list should contain two transaction details");
        assertTrue(tx.getTransactions().contains(transactionDetail1), "The transactions list should contain transactionDetail1");
        assertTrue(tx.getTransactions().contains(transactionDetail2), "The transactions list should contain transactionDetail2");
    }

    
    
    @Test
    void testConstructor() {
        // Arrange
        TransactionDetail transactionDetail1 = new TransactionDetail(1001L, "2025-01-25", "10:00:00", "cr", 100.50, "Payment");
        TransactionDetail transactionDetail2 = new TransactionDetail(1002L, "2025-01-26", "11:00:00", "db", 50.25, "Refund");

        // Act
        CreditCardTransaction tx = new CreditCardTransaction(123, Arrays.asList(transactionDetail1, transactionDetail2));

        // Assert
        assertEquals(123, tx.getCreditCardId(), "The creditCardId should be correctly initialized by the constructor");
        assertEquals(2, tx.getTransactions().size(), "The transactions list should be initialized with two transaction details");
    }

    
    @Test
    void testCanEqual() {
        // Arrange
        CreditCardTransaction tx1 = new CreditCardTransaction();
        CreditCardTransaction tx2 = new CreditCardTransaction();

        // Act & Assert
        assertTrue(tx1.canEqual(tx2), "canEqual should return true for objects of the same type");
        assertFalse(tx1.canEqual(new Object()), "canEqual should return false for objects of different types");
    }

}


