package com.ccms.service.service.impl;

import com.ccms.service.model.CreditCard;
import com.ccms.service.model.CreditCard.CreditCardDetail;
import com.ccms.service.model.Transaction;
import com.ccms.service.model.Transaction.CreditCardTransaction;
import com.ccms.service.model.Transaction.TransactionDetail;
import com.ccms.service.model.TransactionWithCardId;
import com.ccms.service.repository.CustomerRepository;
import com.ccms.service.repository.TransactionRepository;
import com.ccms.service.service.CreditCardService;
import com.ccms.service.utilities.CreditCardEnDecryption;
import com.ccms.service.utilities.CreditCardFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Aggregation;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TransactionServiceimplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CreditCardService cardService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CreditCardEnDecryption cardEnDecryption;

    @Mock
    private CreditCardFormatter cardFormatter;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private TransactionServiceimpl transactionServiceimpl;

    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pageable = PageRequest.of(0, 10); // Example Pageable for pagination
    }
    
    // Test for getTransactionsForUser with valid username
    @Test
    public void testGetTransactionsForUser_Valid() {
        // Mock the transaction data
        TransactionDetail transactionDetail = new TransactionDetail(1L, "2025-01-23", "10:00", "db", 100.0, "Payment to Vendor");
        TransactionWithCardId transactionWithCardId = new TransactionWithCardId(123, transactionDetail);
        
        List<TransactionWithCardId> transactions = Collections.singletonList(transactionWithCardId);

        // Mock the AggregationResults for the aggregation query
        AggregationResults<TransactionWithCardId> aggregationResults = mock(AggregationResults.class);
        when(aggregationResults.getMappedResults()).thenReturn(transactions);

        // Mock the count aggregation
        AggregationResults<Map> countResults = mock(AggregationResults.class);
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("totalCount", 1);
        when(countResults.getUniqueMappedResult()).thenReturn(countMap);

        // Mock the aggregation calls to MongoTemplate
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("Transactions"), eq(TransactionWithCardId.class)))
                .thenReturn(aggregationResults);
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("Transactions"), eq(Map.class)))
                .thenReturn(countResults);

        // Act: Call the service method
        Page<TransactionWithCardId> result = transactionServiceimpl.getTransactionsForUser("user123", pageable);

        // Assert: Verify that the result contains the transaction details
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(123, result.getContent().get(0).getCreditCardId());
        assertEquals(100.0, result.getContent().get(0).getTransactionDetail().getTransactionAmount());
        assertEquals("Payment to Vendor", result.getContent().get(0).getTransactionDetail().getTransactionDesc());
    }

    // Test for getTransactionsForUser with no transactions found
    @Test
    public void testGetTransactionsForUser_NoTransactions() {
        // Mock the AggregationResults for the aggregation query with empty list
        AggregationResults<TransactionWithCardId> aggregationResults = mock(AggregationResults.class);
        when(aggregationResults.getMappedResults()).thenReturn(Collections.emptyList());

        // Mock the count aggregation with total count = 0
        AggregationResults<Map> countResults = mock(AggregationResults.class);
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("totalCount", 0);
        when(countResults.getUniqueMappedResult()).thenReturn(countMap);

        // Mock the aggregation calls to MongoTemplate
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("Transactions"), eq(TransactionWithCardId.class)))
                .thenReturn(aggregationResults);
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("Transactions"), eq(Map.class)))
                .thenReturn(countResults);

        // Act: Call the service method
        Page<TransactionWithCardId> result = transactionServiceimpl.getTransactionsForUser("user123", pageable);

        // Assert: Verify that the result has no transactions
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());
    }

    // Test for getTransactionsForUser when an exception occurs
    @Test
    public void testGetTransactionsForUser_Exception() {
        // Mock the aggregation to throw an exception
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("Transactions"), eq(TransactionWithCardId.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert: Ensure that an exception is thrown when calling the service method
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        	transactionServiceimpl.getTransactionsForUser("user123", pageable);
        });

        // Assert: Check the exception message
        assertEquals("Database error", exception.getMessage());
    }

    // Test for getTransactionsForUser with invalid username (non-existent)
    @Test
    public void testGetTransactionsForUser_CustomerNotFound() {
        // Mock the scenario where no transactions are found for the username
        AggregationResults<TransactionWithCardId> aggregationResults = mock(AggregationResults.class);
        when(aggregationResults.getMappedResults()).thenReturn(Collections.emptyList()); // Return empty list for no transactions

        // Mock the count aggregation with total count = 0
        AggregationResults<Map> countResults = mock(AggregationResults.class);
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("totalCount", 0);  // Total count should be 0 since no transactions found
        when(countResults.getUniqueMappedResult()).thenReturn(countMap);

        // Mock the aggregation calls to MongoTemplate
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("Transactions"), eq(TransactionWithCardId.class)))
                .thenReturn(aggregationResults);  // Return mocked results for transactions aggregation
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("Transactions"), eq(Map.class)))
                .thenReturn(countResults);  // Return mocked results for count aggregation

        // Act: Call the service method
        Page<TransactionWithCardId> result = transactionServiceimpl.getTransactionsForUser("nonexistentUser", pageable);

        // Assert: Verify that the result is empty (no transactions found)
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());  // Verify no elements (transactions)
        assertTrue(result.getContent().isEmpty());  // Verify content is empty
    }

}
