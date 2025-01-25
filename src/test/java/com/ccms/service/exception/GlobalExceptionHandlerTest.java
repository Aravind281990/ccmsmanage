package com.ccms.service.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    // Setup MockMvc with the controller and GlobalExceptionHandler
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler()) // Inject GlobalExceptionHandler
                .build();
    }

//    @Test
//    public void testHandleInvalidUsernameException() throws Exception {
//        String username = "invalidUser";
//        String errorMessage = "Invalid username";
//
//        // Simulate the InvalidUsernameException
//        mockMvc.perform(get("/test-invalid-username")
//                .param("username", username))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid username"))
//                .andExpect(jsonPath("$.details").value(errorMessage));
//    }

    @Test
    public void testHandleDuplicateCreditCardException() throws Exception {
        String errorMessage = "Credit card already associated with this user";

        // Simulate the DuplicateCreditCardException
        mockMvc.perform(get("/test-duplicate-credit-card"))
                .andExpect(status().isBadRequest())
         //       .andExpect(jsonPath("$.message").value("Credit card already associated with this user"))
                .andExpect(jsonPath("$.details").value(errorMessage));
    }

    @Test
    public void testHandleGlobalException() throws Exception {
        String errorMessage = "An unexpected error occurred";

        // Simulate a generic exception
        mockMvc.perform(get("/test-global-exception"))
                .andExpect(status().isInternalServerError())
       //         .andExpect(jsonPath("$.message").value("Internal Server Error"))
                .andExpect(jsonPath("$.details").value(errorMessage));
    }

    // Mock Controller to simulate exceptions
    @RestController
    public static class TestController {

        @GetMapping("/test-invalid-username")
        public void triggerInvalidUsernameException(@RequestParam String username) {
            throw new InvalidUsernameException("Invalid username for: " + username);
        }

        @GetMapping("/test-duplicate-credit-card")
        public void triggerDuplicateCreditCardException() {
            throw new DuplicateCreditCardException("Credit card already associated with this user");
        }

        @GetMapping("/test-global-exception")
        public void triggerGlobalException() {
            throw new RuntimeException("Global error");
        }
    }
}
