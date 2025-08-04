package com.yourorg.classroom.gui;

import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

/**
 * Unit tests for EnvironmentControlClient. We verify:
 * - GET /data always returns the raw JSON payload (or silence on error),
 * - POST /settings returns status message or suppresses exceptions.
 *
 * We wire a custom RestTemplate into the client to use Spring's
 * MockRestServiceServer for deterministic behavior. This avoids mocking the
 * final RestTemplate class directly.
 */
class EnvironmentControlClientTest {

    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private EnvironmentControlClient client;

    @BeforeEach
    void setup() {
        // Create RestTemplate and wire up a mock HTTP server
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();

        // Provide a dummy Stage (bare minimum) and the mock RestTemplate
        client = new EnvironmentControlClient(new Stage(), restTemplate);
    }

    @AfterEach
    void verifyMocks() {
        mockServer.verify();
    }

    @Test
    void whenGetEnvironmentDataSucceeds_returnsRawJson() {
        String sampleJson = "{\"temperature\":22.5,\"humidity\":58}";
        mockServer.expect(requestTo("http://localhost:8080/data"))
                  .andExpect(method(HttpMethod.GET))
                  .andRespond(withSuccess(sampleJson, MediaType.APPLICATION_JSON));

        assertDoesNotThrow(() -> client.fetchEnvironmentData());

        // Internally, fetchEnvironmentData sets a field; verify via reflection:
        String result = client.getLastFetchedData();
        assertEquals(sampleJson, result);
    }

    @Test
    void whenGetEnvironmentDataServerError_logsAndSuppressesException() {
        mockServer.expect(requestTo("http://localhost:8080/data"))
                  .andExpect(method(HttpMethod.GET))
                  .andRespond(withServerError());

        assertDoesNotThrow(() -> client.fetchEnvironmentData());

        // Since server error, lastFetchedData should remain null
        assertNull(client.getLastFetchedData());
    }

    @Test
    void whenUpdateEnvironmentSuccess_returnsConfirmation() {
        String requestBody = "{\"temperature\":21.0,\"humidity\":60}";
        String responseBody = "OK";

        mockServer.expect(requestTo("http://localhost:8080/settings"))
                  .andExpect(method(HttpMethod.POST))
                  .andExpect(content().json(requestBody))
                  .andRespond(withSuccess(responseBody, MediaType.TEXT_PLAIN));

        assertDoesNotThrow(() -> client.updateEnvironmentSettings(21.0, 60));

        String lastResponse = client.getLastPostResponse();
        assertEquals(responseBody, lastResponse);
    }

    @Test
    void whenUpdateEnvironmentError_logsAndSuppressException() {
        mockServer.expect(requestTo("http://localhost:8080/settings"))
                  .andExpect(method(HttpMethod.POST))
                  .andRespond(withStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR));

        assertDoesNotThrow(() -> client.updateEnvironmentSettings(25.0, 50));

        assertNull(client.getLastPostResponse());
    }
}
