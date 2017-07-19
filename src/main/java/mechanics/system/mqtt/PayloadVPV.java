package mechanics.system.mqtt;

import com.google.gson.JsonObject;
import org.jglue.fluentjson.JsonBuilderFactory;

/**
 * Created by Alex Storm on 24.05.2017.
 */
public class PayloadVPV {
    private String id;
    private String model = "r2d2";
    private String name = "Pump";
    private int abnormalVibrationMain = 1;
    private int abnormalVibrationBlower = 1;
    private int maxDistanceMain = 1;
    private int maxDistanceBlower = 1;


    private PayloadVPV() {

    }

    private String initialize() {
        JsonObject jsonObject = JsonBuilderFactory.buildObject()
                .addObject("device")
                .add("id", id)
                .add("model", model)
                .add("name", name)
                .end()
                .addObject("vibration")
                .addArray("accelerometer")
                .addObject()

                .addObject("filteredMax")
                .add("abortLimit", 0)
                .add("abortState", false)
                .add("alarmLimit", 0)
                .add("alarmState", false)
                .add("distance", 0)
                .end()

                .addObject("filteredRms")
                .add("abortLimit", 0)
                .add("abortState", false)
                .add("alarmLimit", 0)
                .add("alarmState", false)
                .add("distance", 0)
                .end()

                .add("freqAtMaxDistance", 6914)
                .addObject("max")
                .add("abortLimit", 0)
                .add("abortState", false)
                .add("alarmLimit", 0)
                .add("alarmState", false)
                .add("distance", maxDistanceMain)
                .end()

                .add("pumpFailureState", true)
                .addObject("rms")
                .add("abortLimit", 0)
                .add("abortState", false)
                .add("alarmLimit", 0)
                .add("alarmState", false)
                .add("distance", 0)
                .end()

                .addArray("userDefined")
                .addObject()
                .add("distance", abnormalVibrationMain)
                .add("freq", 1000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationMain)
                .add("freq", 2000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationMain)
                .add("freq", 3000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationMain)
                .add("freq", 4000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationMain)
                .add("freq", 5000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationMain)
                .add("freq", 6000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationMain)
                .add("freq", 7000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationMain)
                .add("freq", 8000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationMain)
                .add("freq", 9000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationMain)
                .add("freq", 10000)
                .end()

                .end()
                .end()

                .addObject()
                .addObject("filteredMax")
                .add("abortLimit", 0)
                .add("abortState", false)
                .add("alarmLimit", 0)
                .add("alarmState", false)
                .add("distance", 0)
                .end()

                .addObject("filteredRms")
                .add("abortLimit", 0)
                .add("abortState", false)
                .add("alarmLimit", 0)
                .add("alarmState", false)
                .add("distance", 0)
                .end()

                .add("freqAtMaxDistance", 6914)
                .addObject("max")
                .add("abortLimit", 90)
                .add("abortState", false)
                .add("alarmLimit", 0)
                .add("alarmState", false)
                .add("distance", maxDistanceBlower)
                .end()

                .add("pumpFailureState", true)
                .addObject("rms")
                .add("abortLimit", 0)
                .add("abortState", false)
                .add("alarmLimit", 0)
                .add("alarmState", false)
                .add("distance", 0)
                .end()


                .addArray("userDefined")
                .addObject()
                .add("distance", abnormalVibrationBlower)
                .add("freq", 1000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationBlower)
                .add("freq", 2000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationBlower)
                .add("freq", 3000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationBlower)
                .add("freq", 4000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationBlower)
                .add("freq", 5000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationBlower)
                .add("freq", 6000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationBlower)
                .add("freq", 7000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationBlower)
                .add("freq", 8000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationBlower)
                .add("freq", 9000)
                .end()

                .addObject()
                .add("distance", abnormalVibrationBlower)
                .add("freq", 10000)
                .end()

                .end()
                .end()
                .end()


                .addObject("alarm")
                .add("abort", false)
                .add("failure", false)
                .add("state", false)
                .end()

                .add("serialNum", 0)
                .add("timestamp", System.currentTimeMillis())

                .getJson();
        return jsonObject.toString();
    }

    public static Builder newBuilder() {
        return new PayloadVPV().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder setId(String id) {
            PayloadVPV.this.id = id;
            return this;
        }

        public Builder setModel(String model) {
            PayloadVPV.this.model = model;
            return this;
        }

        public Builder setName(String name) {
            PayloadVPV.this.name = name;
            return this;
        }

        public Builder setAbnormalVibrationMain(int distance) {
            PayloadVPV.this.abnormalVibrationMain = distance;
            return this;
        }

        public Builder setAbnormalVibrationBlower(int distance) {
            PayloadVPV.this.abnormalVibrationBlower = distance;
            return this;
        }

        public Builder setMaxDistanceMain(int maxDistanceMain) {
            PayloadVPV.this.maxDistanceMain = maxDistanceMain;
            return this;
        }

        public Builder setMaxDistanceBlower(int maxDistanceBlower) {
            PayloadVPV.this.maxDistanceBlower = maxDistanceBlower;
            return this;
        }

        public String initialize() {
            PayloadVPV payloadVPV = new PayloadVPV();
            payloadVPV.id = PayloadVPV.this.id;
            payloadVPV.model = PayloadVPV.this.model;
            payloadVPV.name = PayloadVPV.this.name;
            payloadVPV.abnormalVibrationMain = PayloadVPV.this.abnormalVibrationMain;
            payloadVPV.abnormalVibrationBlower = PayloadVPV.this.abnormalVibrationBlower;
            payloadVPV.maxDistanceMain = PayloadVPV.this.maxDistanceMain;
            payloadVPV.maxDistanceBlower = PayloadVPV.this.maxDistanceBlower;

            return payloadVPV.initialize();
        }
    }
}
