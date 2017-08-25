package mechanics.system.http;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import mechanics.system.aws.SignAWSv4;
import org.apache.http.conn.ssl.SSLSocketFactory;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static mechanics.system.constant.HTTPMethod.*;

/**
 * Created by Alex Storm on 25.08.2017.
 */
public class RequestFactory {

    public RequestFactory(){
        setUpBaseApiGateway();
    }

    private void setUpBaseApiGateway() {
        SSLSocketFactory customSslFactory = null;
        try {
            customSslFactory = new GatewaySslSocketFactory(
                    SSLContext.getDefault(), SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        RestAssured.config = RestAssured.config().sslConfig(
                SSLConfig.sslConfig().sslSocketFactory(customSslFactory));
        RestAssured.config.getHttpClientConfig().reuseHttpClientInstance();
    }

    public Response sendAmazonRequest(String method, String url) {
        SignAWSv4 signAWSv4 = new SignAWSv4();
        Map<String, String> headers;
        Response response = null;

        switch (method) {
            case "GET":
                headers = signAWSv4.allHeaders(GET.getValue(), url);
                response = createEmptyRequestWithHeaders(headers).get(url);
                break;
            case "OPTIONS":
                headers = signAWSv4.allHeaders(OPTIONS.getValue(), url);
                response = createEmptyRequestWithHeaders(headers).options(url);
                break;
            case "DELETE":
                headers = signAWSv4.allHeaders(DELETE.getValue(), url);
                response = createEmptyRequestWithHeaders(headers).delete(url);
                break;
        }
        return response;
    }

    public Response sendAmazonRequest(String method, String url, String body) {
        SignAWSv4 signAWSv4 = new SignAWSv4();
        Map<String, String> headers;
        Response response = null;

        switch (method) {
            case "POST":
                headers = signAWSv4.allHeaders(POST.getValue(), url, body);
                response = createRequestWithHeaders(headers, body).post(url);
                break;
            case "PUT":
                headers = signAWSv4.allHeaders(PUT.getValue(), url, body);
                response = createRequestWithHeaders(headers, body).put(url);
                break;
            case "DELETE":
                headers = signAWSv4.allHeaders(DELETE.getValue(), url, body);
                response = createRequestWithHeaders(headers, body).delete(url);
                break;
        }
        return response;
    }

    public Response sendRequest(String method, String url) {
        Map<String, String> headers = defaultHeaders();
        Response response = null;

        switch (method) {
            case "GET":
                response = createEmptyRequestWithHeaders(headers).get(url);
                break;
            case "OPTIONS":
                response = createEmptyRequestWithHeaders(headers).options(url);
                break;
            case "DELETE":
                response = createEmptyRequestWithHeaders(headers).delete(url);
                break;
        }
        return response;
    }

    public Response sendRequest(String method, String url, String body) {
        Map<String, String> headers = defaultHeaders();
        Response response = null;

        switch (method) {
            case "POST":
                response = createRequestWithHeaders(headers, body).post(url);
                break;
            case "PUT":
                response = createRequestWithHeaders(headers, body).put(url);
                break;
            case "DELETE":
                response = createRequestWithHeaders(headers, body).delete(url);
                break;
        }
        return response;
    }
    
    private RequestSpecification createEmptyRequestWithHeaders(Map<String, ?> map) {
        return given().when().headers(map);
    }

    private RequestSpecification createRequestWithHeaders(Map<String, ?> map, String body) {
        return given().when().headers(map).body(body);
    }

    private static Map<String, String> defaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Connection", "keep-alive");
        headers.put("Cache-Control", "no-cache");
        headers.put("Pragma", "no-cache");
        headers.put("Content-Type", "application/json");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        headers.put("Accept-Language", "en-US,en;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, sdch, br");
        return headers;
    }
}
