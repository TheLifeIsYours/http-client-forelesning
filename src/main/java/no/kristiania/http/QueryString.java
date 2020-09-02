package no.kristiania.http;

public class QueryString {
    private final String parameterValue;

    public QueryString(String queryString) {
        int equalPos = queryString.indexOf('=');
        parameterValue = queryString.substring(equalPos+1);
    }

    public String getParameter(String status) {
        return parameterValue;
    }
}
