package mechanics.system.equipment;

import mechanics.system.constant.AssembledEquipments;
import mechanics.system.mqtt.MQTTConnector;
import ru.yandex.qatools.allure.annotations.Step;


/**
 * Created by user on 21.04.2017.
 */
public class TriggerVPV extends MQTTConnector {

    public void triggerVPVWidgetAlarmMain() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setMaxDistanceMain(95).setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
        mqttPublishToConnected(topic, payload);
        mqttDisconnect();
    }

    public void triggerVPVWidgetAlarmBlower() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setMaxDistanceBlower(95).setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
        mqttPublishToConnected(topic, payload);
        mqttDisconnect();
    }

    public void triggerVPVWidgetAbortMain() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setMaxDistanceMain(666).setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
        mqttPublishToConnected(topic, payload);
        mqttDisconnect();
    }

    public void triggerVPVWidgetAbortBlower() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setMaxDistanceBlower(666).setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
        mqttPublishToConnected(topic, payload);
        mqttDisconnect();
    }

    public void triggerVPVNormal() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
        mqttPublishToConnected(topic, payload);
        mqttDisconnect();
    }

    @Step("Sending payload with Abort to VPV equipment on Main channel")
    public void triggerVPVAbnormalVibrationMainAbort() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setAbnormalVibrationMain(666).setId(AssembledEquipments.equipmentVpv).initialize();
        String payloadNormal = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
        mqttDisconnect();
    }

    @Step("Sending payload with Alarm to VPV equipment on Main channel")
    public void triggerVPVAbnormalVibrationMainAlarm() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setAbnormalVibrationMain(95).setMaxDistanceMain(95).setId(AssembledEquipments.equipmentVpv).initialize();
        String payloadNormal = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
//        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
//        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttDisconnect();
    }

    @Step("Sending payload with Abort to VPV equipment on Blower channel")
    public void triggerVPVAbnormalVibrationBlowerAbort() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setAbnormalVibrationBlower(666).setId(AssembledEquipments.equipmentVpv).initialize();
        String payloadNormal = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
//        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
//        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttDisconnect();
    }

    @Step("Sending payload with Abort to VPV equipment on Blower channel")
    public void triggerVPVAbnormalVibrationBlowerAlarm() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setAbnormalVibrationBlower(96).setId(AssembledEquipments.equipmentVpv).initialize();
        String payloadNormal = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
//        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payload);
//        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttDisconnect();
    }

    @Step("Sending payload to VPV equipment with Abort")
    public void triggerVPVAbort() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setMaxDistanceMain(666).setMaxDistanceBlower(666).setId(AssembledEquipments.equipmentVpv).initialize();
        String payloadNormal = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
//        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payload);
//        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttDisconnect();
    }

    public void triggerVPVALLEXPLODED() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setAbnormalVibrationMain(666).setAbnormalVibrationBlower(666).setMaxDistanceMain(666).setMaxDistanceBlower(666).setId(AssembledEquipments.equipmentVpv).initialize();
        String payloadNormal = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
//        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payload);
//        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttDisconnect();
    }

    @Step("Sending payload to VPV equipment with Abort")
    public void triggerVPVAlarm() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setMaxDistanceMain(100).setId(AssembledEquipments.equipmentVpv).initialize();
        String payloadNormal = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
//        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payload);
//        mqttPublishToConnected(topic, payload);
//        mqttPublishToConnected(topic, payload);
//        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttDisconnect();
    }


    public void triggerVPVDisconected() {
        String topic = "";
        String payload = "{\"expiration\":" + (System.currentTimeMillis() / 1000 - 600) + "}";
        mqttPublish(topic, payload);
    }

    public void triggerVPVConnected() {
        String topic = "";
        String payload = "{\"expiration\":" + (System.currentTimeMillis() / 1000 + 120) + "}";
        mqttPublish(topic, payload);
    }

    @Step("Sending payload to VPV equipment")
    public void triggerVPVAlarmCount() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).setMaxDistanceMain(91).initialize();
        String payloadNormal = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
//        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttDisconnect();
    }

    @Step("Sending payload to VPV equipment")
    public void triggerVPVAbortCount() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).setMaxDistanceMain(170).initialize();
        String payloadNormal = PayloadVPV.newBuilder().setId(AssembledEquipments.equipmentVpv).initialize();
        mqttConnect();
//        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttDisconnect();
    }

    public void readVPVD11() {
        String topic = AssembledEquipments.equipmentVpvData;
        mqttSubscribe(59000, topic);

    }
}
