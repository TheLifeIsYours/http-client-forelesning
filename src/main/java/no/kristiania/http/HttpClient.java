package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    private int responseCode;
    private Map<String, String> responseHeaders = new HashMap<>();
    public Socket socket;

    public HttpClient(String hostname, int port, String requestTarget) throws IOException {

        Socket socket = setSocket(hostname, port);

        String request = makeRequest("GET", requestTarget, hostname);

        socket.getOutputStream().write(request.getBytes());

        //FETCH RESPONSE CODE
        this.responseCode = parseResponseCode();

        System.out.println(this.responseCode);

        if(this.responseCode == 200) {
            //PARSE RESPONSE HEADERS
            this.responseHeaders = parseResponseHeaders();
        } else {
            System.out.println("Unable to parseResponseHeaders > Response Code: "+ this.responseCode);
        }
    }

    public Map<String, String> parseResponseHeaders() throws IOException {
        String headerLine;

        while (!(headerLine = readLine()).isEmpty()) {
            System.out.println(headerLine);

            int colonPos = headerLine.indexOf(':');
            String fieldName = headerLine.substring(0, colonPos);
            String fieldValue = headerLine.substring(colonPos+1).trim();
            responseHeaders.put(fieldName, fieldValue);
        }

        return this.responseHeaders;
    }

    public int parseResponseCode() throws IOException {
        String responseLine = readLine();

        String[] responseLineParts = responseLine.split(" ");
        this.responseCode = Integer.parseInt(responseLineParts[1]);

        System.out.println("ResponseCode: "+ this.responseCode);

        return this.responseCode;
    }

    private String makeRequest(String requestMethod, String requestTarget, String hostName) {
        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + hostName + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";

        return request;
    }

    private String readLine() throws IOException {
        StringBuilder line = new StringBuilder();

        while (this.hasReadableStream()) {
            char utf8char = (char) this.socket.getInputStream().read();
            System.out.println("utf8 char: "+ utf8char);

            if (utf8char == '\r') {
                //Read next char to get rid of new line "\n"
                this.socket.getInputStream().read();
                break;
            }

            line.append(utf8char);
        }

        return line.toString();
    }

    public boolean hasReadableStream() throws IOException {
        if(this.socket == null) return false;

        int available = this.socket.getInputStream().available();
        System.out.println("Available bytes: "+ available);

        if(available >= 1) return true;

        return  false;
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

    public String getResponseHeader(String headerName) {
        return responseHeaders.get(headerName);
    }

    public Socket setSocket(String hostName, int port) throws IOException {
        if(this.socket == null)
        {
            this.socket = new Socket(hostName, port);
        } else {
            System.out.println("Socket already established @ " + this.socket.getLocalAddress() + " connected on port "+ this.socket.getLocalPort());
        }

        return this.socket;
    }

    public Socket getSocket(){
        return this.socket;
    }
}
