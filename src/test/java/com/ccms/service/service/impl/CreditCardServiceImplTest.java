package com.ccms.service.service.impl;

import com.ccms.service.exception.CreditCardNotFoundException;
import com.ccms.service.exception.CustomerNotFoundException;
import com.ccms.service.exception.DuplicateCreditCardException;
import com.ccms.service.model.CreditCard;
import com.ccms.service.model.CreditCard.CreditCardDetail;
import com.ccms.service.model.Customer;
import com.ccms.service.repository.CreditCardRepository;
import com.ccms.service.repository.CustomerRepository;
import com.ccms.service.utilities.CreditCardEnDecryption;
import com.ccms.service.utilities.CreditCardFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class CreditCardServiceImplTest {

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private CreditCardEnDecryption cardEnDecryption;

    @Mock
    private CreditCardFormatter cardFormatter;

    private Customer mockCustomer;
    private CreditCard mockCreditCard;
    private CreditCardDetail mockCreditCardDetail;

    @BeforeEach
    void setUp() {
        mockCustomer = new Customer();
        mockCustomer.setUsername("testUser");
        mockCustomer.setName(new Customer.Name("John", "Doe"));

        mockCreditCard = new CreditCard();
        mockCreditCard.setUsername("testUser");
        mockCreditCard.setNameOnTheCard("John Doe");
        mockCreditCard.setCreditcards(new ArrayList<>());

        mockCreditCardDetail = new CreditCardDetail();
        mockCreditCardDetail.setCreditCardNumber("1234567812345678");
        mockCreditCardDetail.setCvv(123);
        mockCreditCardDetail.setExpiryMonth(12);
        mockCreditCardDetail.setExpiryYear(2023);
        mockCreditCardDetail.setWireTransactionVendor("Vendor1");
        mockCreditCardDetail.setStatus("enabled");
    }

    @Test
    void testGetCreditCardForUser_ValidUsername() {
        when(customerRepository.findByUsername("testUser")).thenReturn(mockCustomer);
        when(creditCardRepository.findByUsername1("testUser")).thenReturn(mockCreditCard);

        try {
            CreditCard result = creditCardService.getCreditCardForUser("testUser", true);
            assertNotNull(result);
            assertEquals("testUser", result.getUsername());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void testGetCreditCardForUser_CustomerNotFound() {
        when(customerRepository.findByUsername("nonExistentUser")).thenReturn(null);

        assertThrows(CustomerNotFoundException.class, () -> {
            creditCardService.getCreditCardForUser("nonExistentUser", true);
        });
    }

//    @Test
//    void testAddCreditCard_ValidCard() {
//        // Valid scenario where the credit card is added successfully
//        when(customerRepository.findByUsername("testUser")).thenReturn(mockCustomer);
//        when(creditCardRepository.findByUsername1("testUser")).thenReturn(mockCreditCard);
//        when(creditCardRepository.save(any(CreditCard.class))).thenReturn(mockCreditCard);
//
//        try {
//            CreditCardDetail result = creditCardService.addCreditCard("testUser", mockCreditCardDetail);
//            assertNotNull(result);
//            assertEquals(mockCreditCardDetail.getCreditCardNumber(), result.getCreditCardNumber());
//        } catch (Exception e) {
//            fail("Should not throw exception");
//        }
//    }

    @Test
    void testAddCreditCard_CustomerNotFound() {
        when(customerRepository.findByUsername("testUser")).thenReturn(null);

        assertThrows(CustomerNotFoundException.class, () -> {
            creditCardService.addCreditCard("testUser", mockCreditCardDetail);
        });
    }

//    @Test
//    void testAddCreditCard_DuplicateCreditCard() {
//        // Set the valid expiry date (current month and year or later)
//        mockCreditCardDetail.setExpiryYear(LocalDate.now().getYear());  // Valid year
//        mockCreditCardDetail.setExpiryMonth(LocalDate.now().getMonthValue());  // Valid month
//        
//        // Set up the mock customer and credit card repositories
//        when(customerRepository.findByUsername("testUser")).thenReturn(mockCustomer);
//        
//        // Mock the credit card repository to return an existing credit card for "testUser"
//        when(creditCardRepository.findByUsername1("testUser")).thenReturn(mockCreditCard);
//        
//        // Add a mock credit card to simulate an existing card for the user
//        mockCreditCard.getCreditcards().add(mockCreditCardDetail); // Add the card to simulate duplicate
//        
//        // Test: Expecting DuplicateCreditCardException due to duplicate card number
//        assertThrows(DuplicateCreditCardException.class, () -> {
//            creditCardService.addCreditCard("testUser", mockCreditCardDetail);
//        });
//    }

    @Test
    void testToggleCreditCardStatus_ValidCard() {
        // Mock customer and credit card repositories
        when(creditCardRepository.findByUsername1("testUser")).thenReturn(mockCreditCard);

        // Add a mock credit card with a valid ID to the credit card list
        mockCreditCardDetail.setCreditCardId(12345); // Set a valid credit card ID
        mockCreditCard.getCreditcards().add(mockCreditCardDetail);

        // Test toggling the card status
        boolean result = creditCardService.toggleCreditCardStatus("testUser", 12345); // Use valid ID

        // Assert that the result is true
        assertTrue(result);

        // Verify that save method is called (indicating that the status has been updated)
        verify(creditCardRepository, times(1)).save(mockCreditCard);
    }

    @Test
    void testToggleCreditCardStatus_CreditCardNotFound() {
        // Mock customer and credit card repository behavior
        when(creditCardRepository.findByUsername1("testUser")).thenReturn(mockCreditCard);

        // Ensure the mockCreditCard does not contain the card with ID 999
        mockCreditCard.getCreditcards().clear();  // Clear any cards to simulate that no card is found

        // Assert that the CreditCardNotFoundException is thrown
        assertThrows(CreditCardNotFoundException.class, () -> {
            creditCardService.toggleCreditCardStatus("testUser", 999); // Invalid card ID 999
        });

        // Ensure no save method is called since the card was not found
        verify(creditCardRepository, never()).save(any(CreditCard.class));
    }

    @Test
    void testValidateCreditCardDetail_InvalidCardNumber() {
        // Set the invalid credit card number (less than 16 digits)
        mockCreditCardDetail.setCreditCardNumber("12345"); // Invalid number (5 digits)

        // Mock the behavior of the customerRepository to return a valid customer
        when(customerRepository.findByUsername("testUser")).thenReturn(mockCustomer);

        // Expecting an IllegalArgumentException due to invalid card number length
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            creditCardService.addCreditCard("testUser", mockCreditCardDetail);
        });

        // Verify the correct error message is returned
        assertEquals(" Credit card number must be 16 digits", exception.getMessage());
    }
}
