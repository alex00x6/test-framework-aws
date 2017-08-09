package mechanics.system.equipment;

import mechanics.system.constant.AssembledEquipments;
import mechanics.system.mqtt.MQTTConnector;

/**
 * Created by Alex Storm on 27.07.2017.
 */
public class TriggerGPV extends MQTTConnector {


    public void triggerGPVWidgetAlarm() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadGPV.newBuilder().setMultiDataStreamId(AssembledEquipments.equipmentGpvMultiDatastreamId).setAlarm(true).initialize();
        mqttConnect();
        mqttPublishToConnected(topic, payload);
        mqttDisconnect();
    }

    public void triggerGPVNormal() {
        String topic = AssembledEquipments.equipmentVpvData;
        String payload = PayloadGPV.newBuilder().setMultiDataStreamId(AssembledEquipments.equipmentGpvMultiDatastreamId).initialize();
        mqttConnect();
        mqttPublishToConnected(topic, payload);
        mqttDisconnect();
    }

    public void triggerGPVLong() {
        String topic = AssembledEquipments.equipmentGpvData;
        String payload = PayloadGPV.newBuilder().setDistance(240).setMultiDataStreamId(AssembledEquipments.equipmentGpvMultiDatastreamId).setAlarm(true).initialize();
        String payloadNormal = PayloadGPV.newBuilder().setDistance(1).setMultiDataStreamId(AssembledEquipments.equipmentGpvMultiDatastreamId).setAlarm(false).initialize();
        mqttConnect();
        for (int i = 0; i < 5; i++) {
            mqttPublishToConnected(topic, payloadNormal);
            mqttPublishToConnected(topic, payload);
            mqttPublishToConnected(topic, payloadNormal);
        }
        mqttDisconnect();
    }

    public void triggerGPV() {
        String topic = AssembledEquipments.equipmentGpvData;
        String payload = PayloadGPV.newBuilder().setDistance(300).setMultiDataStreamId(AssembledEquipments.equipmentGpvMultiDatastreamId).setAlarm(false).initialize();
        String payloadNormal = PayloadGPV.newBuilder().setDistance(1).setMultiDataStreamId(AssembledEquipments.equipmentGpvMultiDatastreamId).setAlarm(false).initialize();
        mqttConnect();
        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payload);
        mqttPublishToConnected(topic, payloadNormal);
        mqttDisconnect();
    }

    public void triggerGPVAlarm() {
        String topic = AssembledEquipments.equipmentGpvData;
        String payload = PayloadGPV.newBuilder().setMultiDataStreamId(AssembledEquipments.equipmentGpvMultiDatastreamId).setAlarm(true).initialize();
        String payloadNormal = PayloadGPV.newBuilder().setMultiDataStreamId(AssembledEquipments.equipmentGpvMultiDatastreamId).setAlarm(false).initialize();
        mqttConnect();
//        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payloadNormal);
        mqttPublishToConnected(topic, payload);
//        mqttPublishToConnected(topic, payloadNormal);
//        mqttPublishToConnected(topic, payloadNormal);
        mqttDisconnect();
    }

    public void readGPV() {
        String topic = AssembledEquipments.equipmentGpvData;
        mqttSubscribe(59000, topic);
    }

}
