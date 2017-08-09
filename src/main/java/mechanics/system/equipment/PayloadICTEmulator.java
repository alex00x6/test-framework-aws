package mechanics.system.equipment;

import com.google.gson.JsonObject;

/**
 * Created by Alex Storm on 27.07.2017.
 */
public class PayloadICTEmulator {
    private Integer speedMs;
    private Integer slotCount;
    private String userAccessLevel;
    private String operatorName;
    private String status;
    private Double alarmPercentage;
    private Double slotErrorPercentage;
    private Double slotNErrorAdjustment;
    private Double machineErrorPercentage;
    private Double slotStateErrorPercentage;
    private Integer seed;


    private PayloadICTEmulator() {
    }

    public static PayloadICTEmulator.Builder newBuilder() {
        return new PayloadICTEmulator().new Builder();
    }

    private String initialize() {
        JsonObject jsonObject = new JsonObject();
        if (speedMs != null) {
            jsonObject.addProperty("speedMs", speedMs);
        }
        if (slotCount != null) {
            jsonObject.addProperty("slotCount", slotCount);
        }
        if (userAccessLevel != null) {
            jsonObject.addProperty("userAccessLevel", userAccessLevel);
        }
        if (operatorName != null) {
            jsonObject.addProperty("operatorName", operatorName);
        }
        if (status != null) {
            jsonObject.addProperty("status", status);
        }
        if (alarmPercentage != null) {
            jsonObject.addProperty("alarmPercentage", alarmPercentage);
        }
        if (slotErrorPercentage != null) {
            jsonObject.addProperty("slotErrorPercentage", slotErrorPercentage);
        }
        if (slotNErrorAdjustment != null) {
            jsonObject.addProperty("slotNErrorAdjustment", slotNErrorAdjustment);
        }
        if (machineErrorPercentage != null) {
            jsonObject.addProperty("machineErrorPercentage", machineErrorPercentage);
        }
        if (slotStateErrorPercentage != null) {
            jsonObject.addProperty("slotStateErrorPercentage", slotStateErrorPercentage);
        }
        if (seed != null) {
            jsonObject.addProperty("seed", seed);
        }

        return jsonObject.toString();
    }

    public class Builder {
        private Builder() {

        }

        public PayloadICTEmulator.Builder setSpeedMs(int speedMs) {
            PayloadICTEmulator.this.speedMs = speedMs;
            return this;
        }

        public PayloadICTEmulator.Builder setSlotCount(int slotCount) {
            PayloadICTEmulator.this.slotCount = slotCount;
            return this;
        }

        public PayloadICTEmulator.Builder setUserAccessLevel(String userAccessLevel) {
            PayloadICTEmulator.this.userAccessLevel = userAccessLevel;
            return this;
        }

        public PayloadICTEmulator.Builder setOperatorName(String operatorName) {
            PayloadICTEmulator.this.operatorName = operatorName;
            return this;
        }

        public PayloadICTEmulator.Builder setStatus(String status) {
            PayloadICTEmulator.this.status = status;
            return this;
        }

        public PayloadICTEmulator.Builder setAlarmPercentage(double alarmPercentage) {
            PayloadICTEmulator.this.alarmPercentage = alarmPercentage;
            return this;
        }

        public PayloadICTEmulator.Builder setSlotErrorPercentage(double slotErrorPercentage) {
            PayloadICTEmulator.this.slotErrorPercentage = slotErrorPercentage;
            return this;
        }

        public PayloadICTEmulator.Builder setSlotNErrorAdjustment(double slotNErrorAdjustment) {
            PayloadICTEmulator.this.slotNErrorAdjustment = slotNErrorAdjustment;
            return this;
        }

        public PayloadICTEmulator.Builder setMachineErrorPercentage(double machineErrorPercentage) {
            PayloadICTEmulator.this.machineErrorPercentage = machineErrorPercentage;
            return this;
        }

        public PayloadICTEmulator.Builder setSlotStateErrorPercentage(double slotStateErrorPercentage) {
            PayloadICTEmulator.this.slotStateErrorPercentage = slotStateErrorPercentage;
            return this;
        }

        public PayloadICTEmulator.Builder setSeed(int seed) {
            PayloadICTEmulator.this.seed = seed;
            return this;
        }

        public String initialize() {
            PayloadICTEmulator payloadICTEmulator = new PayloadICTEmulator();
            payloadICTEmulator.speedMs = PayloadICTEmulator.this.speedMs;
            payloadICTEmulator.slotCount = PayloadICTEmulator.this.slotCount;
            payloadICTEmulator.userAccessLevel = PayloadICTEmulator.this.userAccessLevel;
            payloadICTEmulator.operatorName = PayloadICTEmulator.this.operatorName;
            payloadICTEmulator.status = PayloadICTEmulator.this.status;
            payloadICTEmulator.alarmPercentage = PayloadICTEmulator.this.alarmPercentage;
            payloadICTEmulator.slotErrorPercentage = PayloadICTEmulator.this.slotErrorPercentage;
            payloadICTEmulator.slotNErrorAdjustment = PayloadICTEmulator.this.slotNErrorAdjustment;
            payloadICTEmulator.machineErrorPercentage = PayloadICTEmulator.this.machineErrorPercentage;
            payloadICTEmulator.slotStateErrorPercentage = PayloadICTEmulator.this.slotStateErrorPercentage;
            payloadICTEmulator.seed = PayloadICTEmulator.this.seed;

            return payloadICTEmulator.initialize();
        }

    }
}
