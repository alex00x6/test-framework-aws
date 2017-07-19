package mechanics.system.constant;

/**
 * Created by user on 20.04.2017.
 */
public enum HTTPMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS");

    private final String value;

    HTTPMethod(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }
}
