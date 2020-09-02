package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientTest {

    @Test
    void shouldReadSuccessStatusCode() throws IOException {
        HttpClient client = makeEchoRequest("/echo?status=200");
        assertEquals(200, client.getResponseCode());
    }

    @Test
    void shouldReadFailingStatusCode() throws IOException {
        HttpClient client = makeEchoRequest("/echo?status=401");
        assertEquals(401, client.getResponseCode());
    }

    @Test
    void shouldReadResponseHeaders() throws IOException {
        HttpClient client = makeEchoRequest("/echo?body=Kristiania");
        assertEquals("10", client.getResponseHeader("Content-Length"));
    }

    private HttpClient makeEchoRequest(String requestTarget) throws IOException {
        return new HttpClient("urlecho.appspot.com", 80, requestTarget);
    }

    @Test
    void shouldReturnReadableStreamTrue() throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?body=TestReadableStream");

        boolean hasReadable = client.hasReadableStream();

        while(hasReadable) {
            assertEquals(true, client.hasReadableStream());

            System.out.println(
                    (char) client.socket.getInputStream().read()
            );

            hasReadable = client.hasReadableStream();
        }
    }


}