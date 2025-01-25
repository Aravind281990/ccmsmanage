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

    @Test
    public void testCheckHealthOnStartup_failure() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RuntimeException("Health check failed"));

        // Act
        healthCheckListener.checkHealthOnStartup();

    }

}
