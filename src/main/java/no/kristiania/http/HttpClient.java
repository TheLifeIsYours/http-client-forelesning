package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {

    private int responseCode;

    public HttpClient(String hostname, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(hostname, port);

        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + hostname + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
        socket.getOutputStream().write(request.getBytes());

        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            if (c == '\n') {
                break;
            }
            line.append((char)c);
        }
        String[] responseLineParts = line.toString().split(" ");
        responseCode = Integer.parseInt(responseLineParts[1]);
    }

    public static void main(String[] args) throws IOException {
        String hostname = "urlecho.appspot.com";
        int port = 80;
        String requestTarget = "/echo?status=200&body=Kristiania";
        new HttpClient(hostname, port, requestTarget);
    }

    public int getResponseCode() {
        return responseCode;
    }
}
