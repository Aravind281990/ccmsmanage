package com.ccms.service.utilities;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.ccms.service.kafka.AccessLogKafkaProducer;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



class AccessLogFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private AccessLogKafkaProducer accessLogKafkaProducer;

    @InjectMocks
    private AccessLogFilter accessLogFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inject mock Kafka producer into the filter
        ReflectionTestUtils.setField(accessLogFilter, "accessLogKafkaProducer", accessLogKafkaProducer);
    }

    @Test
    void testLogging() throws Exception {
        // Arrange
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/api/test");
        when(request.getHeader("User-Agent")).thenReturn("TestAgent");
        when(request.getHeader("Referer")).thenReturn("http://example.com");
        when(response.getStatus()).thenReturn(200);

        // Wrap the mocks with ContentCachingRequestWrapper and ContentCachingResponseWrapper
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // Act
        accessLogFilter.doFilter(wrappedRequest, wrappedResponse, chain);

        // Assert that the sendLog method is called once with the correct log message format
        verify(accessLogKafkaProducer, times(1)).sendLog(ArgumentMatchers.anyString());

        // Capture the argument passed to the sendLog method
        ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
        verify(accessLogKafkaProducer).sendLog(logMessageCaptor.capture());
        
        String logMessage = logMessageCaptor.getValue();

        // Optional: You can print out the log message for debugging or assertions
        System.out.println("Captured log message: " + logMessage);

        // Assert that the log message contains expected fields (you can be more strict about the exact content)
        assert logMessage.contains("\"timestamp\":");
        assert logMessage.contains("\"method\": \"GET\"");
        assert logMessage.contains("\"url\": \"/api/test\"");
        assert logMessage.contains("\"ip\": \"127.0.0.1\"");
        assert logMessage.contains("\"status\": 200");
        assert logMessage.contains("\"userAgent\": \"TestAgent\"");
        assert logMessage.contains("\"referer\": \"http://example.com\"");
    }

    // Optional: Add tests for different scenarios like exceptions, etc.
}
