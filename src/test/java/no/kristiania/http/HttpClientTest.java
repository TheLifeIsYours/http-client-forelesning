package no.kristiania.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientTest {

    @Test
    void shouldReadSuccessStatusCode() {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?status=200");
        assertEquals(200, client.getResponseCode());
    }

}