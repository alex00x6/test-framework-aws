package mechanics.system.equipment;

import com.google.gson.JsonObject;
import org.jglue.fluentjson.JsonBuilderFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex Storm on 01.08.2017.
 */
public class PayloadICT {

    private boolean hasProduct = true;
    private String friendlyName = "3";
    private int id = 3;
    private int maxTestTimeInSec = 56;
    private int mode = 2;
    private String productId = "A-212120508.4993";
    private int state = 6;
    private int testResult = 2;
    private String testStartAt;
    private String resultTime;
    private int datastreamId = 3151;



    private PayloadICT() {
        SimpleDateFormat resultFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        resultFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date resultDate = new Date();
        Date startDate = new Date(System.currentTimeMillis()- TimeUnit.HOURS.toMillis(5));
        resultTime = resultFormat.format(resultDate);
        testStartAt = resultFormat.format(startDate);
    }

    public static PayloadICT.Builder newBuilder() {
        return new PayloadICT().new Builder();
    }


    public String initialize() {

        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .addObject("result")

                .add("friendlyName", friendlyName)
                .add("hasProduct", hasProduct)
                .add("id", id)
                .add("maxTestTimeInSec", maxTestTimeInSec)
                .add("mode", mode)
                .add("productId", productId)
                .add("state", state)
                .add("testResult", testResult)
                .add("testStartAt", testStartAt)
                .end()

                .add("resultTime", resultTime)
                .addObject("Datastream")
                .add("@iot.id", datastreamId)

                .getJson();

        return jsonObject.toString();
    }

    public class Builder {
        private Builder() {

        }

        public PayloadICT.Builder setHasProduct(boolean hasProduct) {
            PayloadICT.this.hasProduct = hasProduct;
            return this;
        }

        public PayloadICT.Builder setId(int id) {
            PayloadICT.this.id = id;
            return this;
        }

        public PayloadICT.Builder setMaxTestTimeInSec(int maxTestTimeInSec) {
            PayloadICT.this.maxTestTimeInSec = maxTestTimeInSec;
            return this;
        }

        public PayloadICT.Builder setMode(int mode) {
            PayloadICT.this.mode = mode;
            return this;
        }

        public PayloadICT.Builder setProductId(String productId) {
            PayloadICT.this.productId = productId;
            return this;
        }

        public PayloadICT.Builder setState(int state) {
            PayloadICT.this.state = state;
            return this;
        }

        public PayloadICT.Builder setTestResult(int testResult) {
            PayloadICT.this.testResult = testResult;
            return this;
        }

        public PayloadICT.Builder setTestStartAt(String testStartAt) {
            PayloadICT.this.testStartAt = testStartAt;
            return this;
        }

        public PayloadICT.Builder setResultTime(String resultTime) {
            PayloadICT.this.resultTime = resultTime;
            return this;
        }

        public PayloadICT.Builder setDatastreamId(String datastreamId) {
            PayloadICT.this.datastreamId = Integer.parseInt(datastreamId);
            return this;
        }

        public PayloadICT.Builder setFriendlyName(String friendlyName) {
            PayloadICT.this.friendlyName = friendlyName;
            return this;
        }

        public String initialize() {
            PayloadICT payloadICT = new PayloadICT();
            payloadICT.hasProduct = PayloadICT.this.hasProduct;
            payloadICT.id = PayloadICT.this.id;
            payloadICT.maxTestTimeInSec = PayloadICT.this.maxTestTimeInSec;
            payloadICT.mode = PayloadICT.this.mode;
            payloadICT.productId = PayloadICT.this.productId;
            payloadICT.state = PayloadICT.this.state;
            payloadICT.testResult = PayloadICT.this.testResult;
            payloadICT.testStartAt = PayloadICT.this.testStartAt;
            payloadICT.resultTime = PayloadICT.this.resultTime;
            payloadICT.datastreamId = PayloadICT.this.datastreamId;
            payloadICT.friendlyName=PayloadICT.this.friendlyName;

            return payloadICT.initialize();
        }

    }
}
