package com.ccms.service.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class HealthCheckListenerTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private HealthCheckListener healthCheckListener;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testCheckHealthOnStartup_success() {
//        // Arrange
//        String mockResponse = "{\"status\":\"UP\"}";  // Simulate a healthy response
//        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockResponse);
//
//        // Act
//        healthCheckListener.checkHealthOnStartup();
//
//        // Assert
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
//    }

    @Test
    public void testCheckHealthOnStartup_failure() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RuntimeException("Health check failed"));

        // Act
        healthCheckListener.checkHealthOnStartup();

        // Assert: Verify that exception is caught and an error message is printed
        // You may need to check System.err or log behavior here.
        // For simplicity, you can add a mock check for System.out/err output if needed.

        // Since we are printing, you may use a library like SystemOutRule (from junit 4) or
        // System lambda to capture System.out/err. Below is an example for JUnit 5.
        // You can use libraries like SystemLambda to capture the standard output/error stream.
    }

//    @Test
//    public void testOnApplicationEvent() {
//        // Arrange
//        ApplicationReadyEvent event = mock(ApplicationReadyEvent.class);
//
//        // Act
//        healthCheckListener.onApplicationEvent(event);
//
//        // Assert: Verify that the health check is called when the application is ready
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
//    }
}
