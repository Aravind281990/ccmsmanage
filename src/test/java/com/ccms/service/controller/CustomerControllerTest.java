package com.ccms.service.controller;

import com.ccms.service.exception.InvalidUsernameFormatException;
import com.ccms.service.model.Customer;
import com.ccms.service.service.CustomerService;
import com.ccms.service.utilities.Decodename;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)  // Ensures Mockito mocks are initialized
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private Decodename decodename;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetCustomer_Success() throws Exception {
        // Arrange
        String encodedUsername = "encodedUser123";
        String decodedUsername = "user123";
        Customer customer = new Customer("1", decodedUsername, "password", new Customer.Name("John", "Doe"), 
                                          "1990-01-01", "M", "john.doe@example.com", 123, 
                                          new Customer.Address("123 Main St", "City", "State", 12345, "Country"), true, new java.util.Date());
        
        when(decodename.decodeUsername(encodedUsername)).thenReturn(decodedUsername);
        when(customerService.getCustomer(decodedUsername)).thenReturn(customer);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{username}", encodedUsername)
                        .param("username", encodedUsername)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value(decodedUsername))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name.first").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name.last").value("Doe"));
    }

    @Test
    public void testGetCustomer_InvalidUsernameFormat() throws Exception {
        // Arrange
        String encodedUsername = "invalidEncodedUser";

        when(decodename.decodeUsername(encodedUsername)).thenThrow(new InvalidUsernameFormatException("Invalid format"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{username}", encodedUsername)
                        .param("username", encodedUsername)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
        //        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid username encoding"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Invalid format"));
    }

    @Test
    public void testGetCustomer_CustomerNotFound() throws Exception {
        // Arrange
        String encodedUsername = "encodedUser123";
        String decodedUsername = "user123";
        
        when(decodename.decodeUsername(encodedUsername)).thenReturn(decodedUsername);
        when(customerService.getCustomer(decodedUsername)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{username}", encodedUsername)
                        .param("username", encodedUsername)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
             //   .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Customer not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value("No customer found for username: " + decodedUsername));
    }

    @Test
    public void testGetCustomer_InternalServerError() throws Exception {
        // Arrange
        String encodedUsername = "encodedUser123";
        String decodedUsername = "user123";
        
        when(decodename.decodeUsername(encodedUsername)).thenReturn(decodedUsername);
        when(customerService.getCustomer(decodedUsername)).thenThrow(new RuntimeException("Internal error"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{username}", encodedUsername)
                        .param("username", encodedUsername)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
        //        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("An error occurred while fetching the customer profile"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Internal error"));
    }
}
