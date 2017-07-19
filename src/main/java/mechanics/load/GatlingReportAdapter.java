package mechanics.load;

import com.jayway.restassured.response.Response;

/**
 * Created by user on 18.04.2017.
 */
public class GatlingReportAdapter {
    private final String reportName = "RecordedSimulation";
    private final String reportName1 = "Simulation";
    private final String reportName2 = "Gatling launch";

    private static ThreadLocal<Long> threadStartTime = new ThreadLocal<>();

    public void gatlingInfoPrintUserStart() {
        long thread = Thread.currentThread().getId();
        long timeStart = System.currentTimeMillis();
        threadStartTime.set(timeStart);
        System.out.println("USER\t" + reportName + "\t" + thread + "\tSTART\t" + timeStart + "\t" + timeStart);
    }

    public void gatlingInfoPrintUserEnd() {
        // USER    | NAME | THREAD | START/END | TIMESTART | TIMESTART(onstart)/TIMESTOP(onstop)
        long thread = Thread.currentThread().getId();
//        long timeStart = System.currentTimeMillis();
        long timeStart = threadStartTime.get();
        long timeEnd = System.currentTimeMillis();

        System.out.println("USER\t" + reportName + "\t" + thread + "\tEND\t" + timeStart + "\t" + timeEnd);
    }

    public void gatlingInfoPrintTestStart() {
        System.out.println("RUN\t" + reportName + "\t" + reportName2 + "\t" + reportName1 + "\t" + System.currentTimeMillis() + "\ts\t2.0");
    }

    public void gatlingInfoPrintRequest(String methodAndUri, Response response, boolean enableGatlingReportMessages, boolean replaceTimeStampsInUrls) {
        //if response with error status code = print error
        //if response with ok status code - check response body
        //if response body contains error messages - check again
        //if body contains "expired":false = print ok (not error in our service)
        //if body contains error/exception/expired/timed our = print error
        if (enableGatlingReportMessages) {
            //we parsing methodAndUri for unix timestamp and replacing it with *
            //this step required in order to greatly reduce weight of report in case of time-generated urls
            String requestName;
            if (replaceTimeStampsInUrls) {
                requestName = methodAndUri.replaceAll("[0-9]{13}", "*************");
            } else {
                requestName = methodAndUri;
            }
            long thread = Thread.currentThread().getId();

            String responseOK = "REQUEST\t" + reportName + "\t" + thread + "\t\t" + requestName + "\t" + (System.currentTimeMillis() - response.time()) + "\t" + System.currentTimeMillis() + "\t" + "OK\t ";
            String responseErrorInBody = "REQUEST\t" + reportName + "\t" + thread + "\t\t" + requestName + "\t" + (System.currentTimeMillis() - response.time()) + "\t" + System.currentTimeMillis() + "\t" + "KO\tstatus.find.in(200,304,201,202,203,204,205,206,207,208,209), but actually found " + response.statusCode() + " with response body contains error : " + response.asString();
            String responseErrorStatusCode = "REQUEST\t" + reportName + "\t" + thread + "\t\t" + requestName + "\t" + (System.currentTimeMillis() - response.time()) + "\t" + System.currentTimeMillis() + "\t" + "KO\tstatus.find.in(200,304,201,202,203,204,205,206,207,208,209), but actually found " + response.statusCode() + " with response body: " + response.asString();


            if (response.statusCode() == 200 || response.statusCode() == 304 || response.statusCode() == 201 || response.statusCode() == 202
                    || response.statusCode() == 203 || response.statusCode() == 204 || response.statusCode() == 205 || response.statusCode() == 206
                    || response.statusCode() == 207 || response.statusCode() == 208 || response.statusCode() == 209) {
                if (!response.asString().contains("error") && !response.asString().contains("exception") && !response.asString().contains("expired") && !response.asString().contains("timed out")) {
                    System.out.println(responseOK);
                } else {
                    if (response.asString().contains("\"expired\":false") || response.asString().contains("\"expired\":true,\"accessKeyId\"")) {
                        System.out.println(responseOK);
                    } else {
                        System.out.println(responseErrorInBody);
                    }
                }
            } else {
                System.out.println(responseErrorStatusCode);
            }
        }
    }
}
