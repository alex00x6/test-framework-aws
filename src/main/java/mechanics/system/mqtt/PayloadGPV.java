package mechanics.system.mqtt;

import com.google.gson.JsonObject;
import org.jglue.fluentjson.JsonBuilderFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex Storm on 24.05.2017.
 */
public class PayloadGPV {
    private String multiDataStreamId;
    private boolean alarm = false;
    private int distance = 1;
    private String date;

    private PayloadGPV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date1 = new Date();
        date = sdf.format(date1);
    }

    public String initialize() {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .addArray("result")
                .add(alarm)
                .add(distance)
                .end()

                .add("resultTime", date)
                .addObject("MultiDatastream")
                .add("@iot.id", multiDataStreamId)

                .addArray("ObservedProperties")
                .addObject()
                .add("@iot.id", 2)
                .end()

                .addObject()
                .add("@iot.id", 1)

                .getJson();

        return jsonObject.toString();
    }

    public static PayloadGPV.Builder newBuilder() {
        return new PayloadGPV().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder setMultiDataStreamId(String multiDataStreamId) {
            PayloadGPV.this.multiDataStreamId = multiDataStreamId;
            return this;
        }

        public Builder setAlarm(boolean alarm) {
            PayloadGPV.this.alarm = alarm;
            return this;
        }

        public Builder setDistance(int distance) {
            PayloadGPV.this.distance = distance;
            return this;
        }

        public String initialize() {
            PayloadGPV payloadGPV = new PayloadGPV();
            payloadGPV.multiDataStreamId = PayloadGPV.this.multiDataStreamId;
            payloadGPV.alarm = PayloadGPV.this.alarm;
            payloadGPV.distance = PayloadGPV.this.distance;

            return payloadGPV.initialize();
        }

    }

}

