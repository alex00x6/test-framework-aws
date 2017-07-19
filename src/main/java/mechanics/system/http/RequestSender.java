package mechanics.system.http;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import mechanics.load.GatlingReportAdapter;
import mechanics.system.aws.SignAWSv4;
import mechanics.system.constant.AssembledUrls;
import mechanics.system.credentials.RoleSwitcher;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static mechanics.system.constant.HTTPMethod.*;

public class RequestSender {
    private RequestSpecification requestSpecification = null;
    public Response response = null;
    private static Date startDate = null;
    private static String currentUserRole = null;

    //these three booleans controls console output messages
    protected boolean messagesEnableGatlingReport = true; //should be used with debug messages off (if you want load.gatling reports to work)
    protected boolean messagesEnableErrorDebugResponse = false;
    protected boolean messagesEnableAllDebugResponse = false;
    protected boolean messagesEnableAWSKeys = true;
    private boolean messagesReplaceTimeStampsInUrls = true; //should be on, if you want to generate small and nimble load.gatling reports


    public RequestSender() {
    }

    public static String getCurrentUserRole() {
        return currentUserRole;
    }

    public static void setCurrentUserRole(String currentUserRole) {
        RequestSender.currentUserRole = currentUserRole;
    }

    public static Date getStartDate() {
        return startDate;
    }

    public static void setStartDate() {
        RequestSender.startDate = new Date();
    }

    public static void checkExpired() {
        if (RoleSwitcher.getCurrentUser().getCredentialsReceiveTime() < (System.currentTimeMillis() - 360000)) {
            RequestSender requestSender = new RequestSender();
            requestSender.sendAmazonRequest(GET.getValue(), AssembledUrls.authenticationRefresh);
        }
    }

    public static void checkExpiredLoad() {
        if (startDate.getTime() < (System.currentTimeMillis() - 360000)) {
            RequestSender requestSender = new RequestSender();
            requestSender.sendAmazonRequest(GET.getValue(), AssembledUrls.authenticationRefresh);
        }
    }

    public RequestSender createEmptyRequestWithHeaders(Map<String, ?> map) {
        requestSpecification = given().when().headers(map);
        return this;
    }

    public RequestSender createRequestWithHeaders(Map<String, ?> map, String body) {
        requestSpecification = given().when().headers(map).body(body);
        return this;
    }

    public RequestSender createRequestSpecification() {
        requestSpecification = given().
                when();
        return this;
    }

    // этот метод сможет добавлять столько угодно хедеров из Map
    public RequestSender addHeaders(Map<String, ?> map) {
        requestSpecification.headers(map);
        return this;
    }

    // этот метод сможет добавлять столько угодно хедеров
    public RequestSender addHeader(String headerName, String headerValue) {
        requestSpecification.header(headerName, headerValue);
        return this;
    }

    private RequestSender addBody(String body) {
        requestSpecification.body(body);
        return this;
    }

    public RequestSender post(String uri) {
        response = requestSpecification.post(uri);
        debugInfoPrint("POST_" + uri);
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintRequest("POST_" + uri, response, messagesEnableGatlingReport, messagesReplaceTimeStampsInUrls);
        return this;
    }

    public RequestSender post(String uri, boolean print) {
        response = requestSpecification.post(uri);
        if (print) {
            debugInfoPrint("POST_" + uri);
            GatlingReportAdapter gatling = new GatlingReportAdapter();
            gatling.gatlingInfoPrintRequest("POST_" + uri, response, messagesEnableGatlingReport, messagesReplaceTimeStampsInUrls);
        }
        return this;
    }

    public RequestSender options(String uri) {
        response = requestSpecification.options(uri);
        debugInfoPrint("OPTIONS_" + uri);
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintRequest("OPTIONS_" + uri, response, messagesEnableGatlingReport, messagesReplaceTimeStampsInUrls);
        return this;
    }

    public RequestSender options(String uri, boolean print) {
        response = requestSpecification.options(uri);
        if (print) {
            debugInfoPrint("OPTIONS_" + uri);
            GatlingReportAdapter gatling = new GatlingReportAdapter();
            gatling.gatlingInfoPrintRequest("OPTIONS_" + uri, response, messagesEnableGatlingReport, messagesReplaceTimeStampsInUrls);
        }
        return this;
    }

    public RequestSender delete(String uri) {
        response = requestSpecification.delete(uri);
        debugInfoPrint("DELETE_" + uri);
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintRequest("DELETE_" + uri, response, messagesEnableGatlingReport, messagesReplaceTimeStampsInUrls);
        return this;
    }

    public RequestSender delete(String uri, boolean print) {
        response = requestSpecification.delete(uri);
        if (print) {
            debugInfoPrint("DELETE_" + uri);
            GatlingReportAdapter gatling = new GatlingReportAdapter();
            gatling.gatlingInfoPrintRequest("DELETE_" + uri, response, messagesEnableGatlingReport, messagesReplaceTimeStampsInUrls);
        }
        return this;
    }

    public RequestSender get(String uri) {
        response = requestSpecification.get(uri);
        debugInfoPrint("GET_" + uri);
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintRequest("GET_" + uri, response, messagesEnableGatlingReport, messagesReplaceTimeStampsInUrls);
        checkExpiredCredentials(response, uri);
        return this;
    }

    public RequestSender get(String uri, boolean print) {
        response = requestSpecification.get(uri);
        if (print) {
            debugInfoPrint("GET_" + uri);
            GatlingReportAdapter gatling = new GatlingReportAdapter();
            gatling.gatlingInfoPrintRequest("GET_" + uri, response, messagesEnableGatlingReport, messagesReplaceTimeStampsInUrls);
            checkExpiredCredentials(response, uri);
        }
        return this;
    }

    public RequestSender put(String uri) {
        response = requestSpecification.put(uri);
        debugInfoPrint("PUT_" + uri);
        GatlingReportAdapter gatling = new GatlingReportAdapter();
        gatling.gatlingInfoPrintRequest("PUT_" + uri, response, messagesEnableGatlingReport, messagesReplaceTimeStampsInUrls);
        return this;
    }

    public RequestSender put(String uri, boolean print) {
        response = requestSpecification.put(uri);
        if (print) {
            debugInfoPrint("PUT_" + uri);
            GatlingReportAdapter gatling = new GatlingReportAdapter();
            gatling.gatlingInfoPrintRequest("PUT_" + uri, response, messagesEnableGatlingReport, messagesReplaceTimeStampsInUrls);
        }
        return this;
    }

    public String extractResponseByPath(String path) {
        return response.then().extract().path(path);
    }

    public String extractAllResponseAsString() {
        return response.then().extract().asString();
    }

    public Response getResponse() {
        return response;
    }

    private void checkExpiredCredentials(Response response, String url) {
        if (url.contains("authentication/refresh")) {
            if (response.asString().contains("\"expired\":true")) {
                JSONParser parser = new JSONParser();
                JSONObject json = null;
                try {
                    json = (JSONObject) parser.parse(response.asString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //write new creds to credential storage
                RoleSwitcher.getCurrentUser().getAwsKeys().setAccessKeyId(json.get("accessKeyId").toString());
                RoleSwitcher.getCurrentUser().getAwsKeys().setSecretAccessKey(json.get("secretAccessKey").toString());
                RoleSwitcher.getCurrentUser().getAwsKeys().setSessionToken(json.get("sessionToken").toString());
                RoleSwitcher.getCurrentUser().setCredentialsReceiveTimeNow();
            }
        }
    }


    private void debugInfoPrint(String url) {
        if (messagesEnableErrorDebugResponse || messagesEnableAllDebugResponse) {
            if (response.statusCode() != 200 || response.asString().contains("error") || messagesEnableAllDebugResponse) {
                System.out.println("==================================");
                System.out.println("Request: ");
                System.out.println(url);
                if (messagesEnableAWSKeys) {
                    System.out.println("Access key: " + RoleSwitcher.getCurrentUser().getAwsKeys().getAccessKeyId());
                    System.out.println("Secret access key: " + RoleSwitcher.getCurrentUser().getAwsKeys().getSecretAccessKey());
                    System.out.println("Session token: " + RoleSwitcher.getCurrentUser().getAwsKeys().getSessionToken());
                }
                System.out.println("-------------------------------");
                System.out.println("Response time: " + response.time());
                System.out.println("Status code: " + response.statusCode());
                System.out.println("-------------------------------");
                System.out.println(response.headers().toString());
                System.out.println("-------------------------------");
                System.out.println(response.asString());
                System.out.println("==================================");
            }
        }
    }

    public void setUpBaseApiGateway() {
        //это вынести по ходу в listener для api/load тестов
        // Use our custom socket factory
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
                response = createEmptyRequestWithHeaders(headers).get(url).getResponse();
                break;
            case "OPTIONS":
                headers = signAWSv4.allHeaders(OPTIONS.getValue(), url);
                response = createEmptyRequestWithHeaders(headers).options(url).getResponse();
                break;
            case "DELETE":
                headers = signAWSv4.allHeaders(DELETE.getValue(), url);
                response = createEmptyRequestWithHeaders(headers).delete(url).getResponse();
                break;
        }
        return response;
    }

    public Response sendAmazonRequest(String method, String url, boolean print) {
        SignAWSv4 signAWSv4 = new SignAWSv4();
        Map<String, String> headers;
        Response response = null;

        switch (method) {
            case "GET":
                headers = signAWSv4.allHeaders(GET.getValue(), url);
                response = createEmptyRequestWithHeaders(headers).get(url, print).getResponse();
                break;
            case "OPTIONS":
                headers = signAWSv4.allHeaders(OPTIONS.getValue(), url);
                response = createEmptyRequestWithHeaders(headers).options(url, print).getResponse();
                break;
            case "DELETE":
                headers = signAWSv4.allHeaders(DELETE.getValue(), url);
                response = createEmptyRequestWithHeaders(headers).delete(url, print).getResponse();
                break;
        }
        return response;
    }

    public Response sendAmazonRequest(String method, String url, String body) {
        SignAWSv4 signAWSv4 = new SignAWSv4();
        Map<String, String> headers = null;
        Response response = null;

        switch (method) {
            case "POST":
                headers = signAWSv4.allHeaders(POST.getValue(), url, body);
                response = createRequestWithHeaders(headers, body).post(url).getResponse();
                break;
            case "PUT":
                headers = signAWSv4.allHeaders(PUT.getValue(), url, body);
                response = createRequestWithHeaders(headers, body).put(url).getResponse();
                break;
            case "DELETE":
                headers = signAWSv4.allHeaders(DELETE.getValue(), url, body);
                response = createRequestWithHeaders(headers, body).delete(url).getResponse();
                break;
        }
        return response;
    }

    public Response sendAmazonRequest(String method, String url, String body, boolean print) {
        SignAWSv4 signAWSv4 = new SignAWSv4();
        Map<String, String> headers = null;
        Response response = null;

        switch (method) {
            case "POST":
                headers = signAWSv4.allHeaders(POST.getValue(), url, body);
                response = createRequestWithHeaders(headers, body).post(url, print).getResponse();
                break;
            case "PUT":
                headers = signAWSv4.allHeaders(PUT.getValue(), url, body);
                response = createRequestWithHeaders(headers, body).put(url, print).getResponse();
                break;
            case "DELETE":
                headers = signAWSv4.allHeaders(DELETE.getValue(), url, body);
                response = createRequestWithHeaders(headers, body).delete(url, print).getResponse();
                break;
        }
        return response;
    }

}
