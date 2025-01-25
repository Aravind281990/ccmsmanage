package com.ccms.service.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.mongodb.core.MongoTemplate;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CustomHealthIndicator.
 */
public class CustomHealthIndicatorTest {

    // Mock the MeterRegistry and MongoTemplate
    @Mock
    private MeterRegistry meterRegistry;

    @Mock
    private MongoTemplate mongoTemplate;

    // Instance of the class to test
    private CustomHealthIndicator customHealthIndicator;

    @BeforeEach
    public void setup() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Create the CustomHealthIndicator instance with mocks
        customHealthIndicator = new CustomHealthIndicator();
        customHealthIndicator.meterRegistry = meterRegistry;
        customHealthIndicator.mongoTemplate = mongoTemplate;
    }

    @Test
    public void testHealth_up_whenMongoIsReachable() {
        // Arrange: Simulate a successful MongoDB connection (Mongo is up)
        when(mongoTemplate.executeCommand("{ ping: 1 }")).thenReturn(null);  // Return null to indicate success

        // Act: Call the health check method
        Health health = customHealthIndicator.health();

        // Assert: Verify that the health status is UP
        assertEquals(Health.up().withDetail("status", "UP").build(), health);

        // Verify that the meterRegistry.gauge() is called with correct parameters
        verify(meterRegistry, times(1)).gauge(eq("spring_boot_health_status"), 
            eq(Tags.of("status", "UP")), eq(1));
    }

    @Test
    public void testHealth_down_whenMongoIsNotReachable() {
        // Arrange: Simulate a failed MongoDB connection (Mongo is down)
        doThrow(new RuntimeException("MongoDB is not reachable")).when(mongoTemplate).executeCommand("{ ping: 1 }");

        // Act: Call the health check method
        Health health = customHealthIndicator.health();

        // Assert: Verify that the health status is DOWN
        assertEquals(Health.down().withDetail("status", "DOWN").build(), health);

        // Verify that the meterRegistry.gauge() is called with correct parameters
        verify(meterRegistry, times(1)).gauge(eq("spring_boot_health_status"), 
            eq(Tags.of("status", "DOWN")), eq(0));
    }
}
