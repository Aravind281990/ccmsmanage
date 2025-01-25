package com.ccms.service.service.impl;

import com.ccms.service.model.Customer;
import com.ccms.service.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceimpl customerService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Creating the nested Name object
        Customer.Name name = new Customer.Name("John", "Doe");

        // Creating the nested Address object
        Customer.Address address = new Customer.Address("1234 Elm St", "Metropolis", "NY", 12345, "USA");

        // Creating a Customer object
        customer = new Customer(
                "1", 
                "user123", 
                "password123", 
                name, 
                "1990-01-01", 
                "M", 
                "user123@example.com", 
                1001, 
                address, 
                true, 
                new java.util.Date()
        );
    }

    // Test for getCustomer method - Valid username
    @Test
    public void testGetCustomer_ValidUsername() {
        // Arrange: Mock the repository to return the customer for the given username
        when(customerRepository.findByUsername("user123")).thenReturn(customer);

        // Act: Call the service method
        Customer result = customerService.getCustomer("user123");

        // Assert: Check if the result matches the expected customer
        assertNotNull(result);
        assertEquals("user123", result.getUsername());
        assertEquals("John", result.getName().getFirst());
        assertEquals("Doe", result.getName().getLast());
        assertEquals("user123@example.com", result.getEmail());
        assertEquals("1234 Elm St", result.getAddress().getStreet());

        // Verify that the repository method was called exactly once
        verify(customerRepository, times(1)).findByUsername("user123");
    }

    // Test for getCustomer method - Customer not found
    @Test
    public void testGetCustomer_CustomerNotFound() {
        // Arrange: Mock the repository to return null for the given username
        when(customerRepository.findByUsername("nonexistentUser")).thenReturn(null);

        // Act: Call the service method
        Customer result = customerService.getCustomer("nonexistentUser");

        // Assert: Ensure the result is null
        assertNull(result);

        // Verify that the repository method was called exactly once
        verify(customerRepository, times(1)).findByUsername("nonexistentUser");
    }

    // Test for getCustomer method - Repository throws an exception
    @Test
    public void testGetCustomer_Exception() {
        // Arrange: Mock the repository to throw an exception
        when(customerRepository.findByUsername("user123")).thenThrow(new RuntimeException("Database error"));

        // Act & Assert: Ensure an exception is thrown when calling the service method
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.getCustomer("user123");
        });

        // Assert: Check the exception message
        assertEquals("Database error", exception.getMessage());

        // Verify that the repository method was called exactly once
        verify(customerRepository, times(1)).findByUsername("user123");
    }
}
