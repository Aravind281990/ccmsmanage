package com.ccms.service.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    @Test
    void testCreditCardConstructorAndGettersSetters() {
        // Given
        CreditCard.CreditCardDetail detail = new CreditCard.CreditCardDetail(1, "1234567890123456", 12, 25, 123, "Vendor", "Active");
        CreditCard creditCard = new CreditCard("1", "user123", "John Doe", List.of(detail));

        // When
        String cardNumber = creditCard.getCreditcards().get(0).getCreditCardNumber();
        String username = creditCard.getUsername();
        String nameOnCard = creditCard.getNameOnTheCard();

        // Then
        assertNotNull(cardNumber);
        assertEquals("1234567890123456", cardNumber);
        assertEquals("user123", username);
        assertEquals("John Doe", nameOnCard);
    }

    @Test
    void testCreditCardDetailConstructorAndGettersSetters() {
        // Given
        CreditCard.CreditCardDetail detail = new CreditCard.CreditCardDetail(1, "1234567890123456", 12, 25, 123, "Vendor", "Active");

        // When
        int cardId = detail.getCreditCardId();
        String status = detail.getStatus();

        // Then
        assertEquals(1, cardId);
        assertEquals("Active", status);
    }
    
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        CreditCard.CreditCardDetail cardDetail1 = new CreditCard.CreditCardDetail(
                1001, "1234567812345678", 12, 2025, 123, "VendorA", "Active"
        );
        CreditCard.CreditCardDetail cardDetail2 = new CreditCard.CreditCardDetail(
                1001, "1234567812345678", 12, 2025, 123, "VendorA", "Active"
        );
        
        CreditCard creditCard1 = new CreditCard("1", "john_doe", "John Doe", Arrays.asList(cardDetail1));
        CreditCard creditCard2 = new CreditCard("1", "john_doe", "John Doe", Arrays.asList(cardDetail2));

        // Act & Assert
        assertEquals(creditCard1, creditCard2, "The CreditCard objects should be equal");
        assertEquals(creditCard1.hashCode(), creditCard2.hashCode(), "The hash codes should be equal");
        
        // Modify one card's attribute
        creditCard2.setUsername("jane_doe");
        
        assertNotEquals(creditCard1, creditCard2, "The CreditCard objects should not be equal after modification");
        assertNotEquals(creditCard1.hashCode(), creditCard2.hashCode(), "The hash codes should be different after modification");
    }
    
   
    @Test
    void testSetId() {
        // Arrange
        CreditCard.CreditCardDetail cardDetail = new CreditCard.CreditCardDetail(
                1001, "1234567812345678", 12, 2025, 123, "VendorA", "Active"
        );
        CreditCard creditCard = new CreditCard("1", "john_doe", "John Doe", Arrays.asList(cardDetail));

        // Act
        creditCard.setId("2");

        // Assert
        assertEquals("2", creditCard.getId(), "The ID should be updated to '2'");
    }
    
    
    @Test
    void testCanEqual() {
        // Arrange
        CreditCard.CreditCardDetail cardDetail = new CreditCard.CreditCardDetail(
                1001, "1234567812345678", 12, 2025, 123, "VendorA", "Active"
        );
        CreditCard creditCard = new CreditCard("1", "john_doe", "John Doe", Arrays.asList(cardDetail));
        
        // Arrange an object of a different type (e.g., String)
        String differentTypeObject = "Not a CreditCard object";

        // Act & Assert
        assertTrue(creditCard.canEqual(creditCard), "canEqual should return true for objects of the same type");
        assertFalse(creditCard.canEqual(differentTypeObject), "canEqual should return false for objects of different types");
    }

    
    @Test
    void testEqualsWithNull() {
        // Arrange
        CreditCard.CreditCardDetail cardDetail = new CreditCard.CreditCardDetail(
                1001, "1234567812345678", 12, 2025, 123, "VendorA", "Active"
        );
        CreditCard creditCard = new CreditCard("1", "john_doe", "John Doe", Arrays.asList(cardDetail));

        // Act & Assert
        assertNotEquals(creditCard, null, "A CreditCard object should not be equal to null");
    }
    
    
    @Test
    void testEqualsWithDifferentFields() {
        // Arrange
        CreditCard.CreditCardDetail cardDetail1 = new CreditCard.CreditCardDetail(
                1001, "1234567812345678", 12, 2025, 123, "VendorA", "Active"
        );
        CreditCard.CreditCardDetail cardDetail2 = new CreditCard.CreditCardDetail(
                1002, "9876543210987654", 11, 2024, 321, "VendorB", "Inactive"
        );
        
        CreditCard creditCard1 = new CreditCard("1", "john_doe", "John Doe", Arrays.asList(cardDetail1));
        CreditCard creditCard2 = new CreditCard("2", "jane_doe", "Jane Doe", Arrays.asList(cardDetail2));

        // Act & Assert
        assertNotEquals(creditCard1, creditCard2, "The CreditCard objects should not be equal due to different fields");
    }



    
}
