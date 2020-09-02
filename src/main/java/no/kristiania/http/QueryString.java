package no.kristiania.http;

public class QueryString {
    private final String parameterValue;
    private final String parameterName;

    public QueryString(String queryString) {
        int equalPos = queryString.indexOf('=');
        parameterName = queryString.substring(0, equalPos);
        parameterValue = queryString.substring(equalPos+1);
    }

    public String getParameter(String status) {
        if(status.equals(parameterName)) {
            return parameterValue;
        } else  {
            return null;
        }
    }


}
