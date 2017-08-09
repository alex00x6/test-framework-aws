package mechanics.system.equipment;

import mechanics.system.constant.AssembledEquipments;
import mechanics.system.mqtt.MQTTConnector;
import mechanics.ui.utils.WaitsAsserts;

/**
 * Created by Alex Storm on 01.08.2017.
 */
public class TriggerICT extends MQTTConnector {

    public void triggerSMTH() {
        String topic = AssembledEquipments.ictTopic;
        String payload = PayloadICT.newBuilder().setMode(4).setTestResult(0).setDatastreamId(AssembledEquipments.ictDatastream).initialize();
        mqttConnect();
        for (int i = 0; i < 50; i++) {
            mqttPublishToConnected(topic, payload);
        }
        mqttDisconnect();
    }

    public void triggerYieldFade() {
        String topic = AssembledEquipments.ictTopic;
        String payload;
        mqttConnect();
        payload = PayloadICT.newBuilder().setTestResult(3).setDatastreamId(AssembledEquipments.ictDatastream).initialize();
        for (int i = 0; i < 64; i++) {
            mqttPublishToConnected(topic, payload);
            WaitsAsserts.sleep(300);

        }
        mqttDisconnect();
    }

    public void triggerYieldRise() {
        String topic = AssembledEquipments.ictTopic;
        String payload;
        mqttConnect();
        payload = PayloadICT.newBuilder().setTestResult(2).setDatastreamId(AssembledEquipments.ictDatastream).initialize();
        for (int i = 0; i < 64; i++) {
            mqttPublishToConnected(topic, payload);
            WaitsAsserts.sleep(300);

        }
        mqttDisconnect();
    }

}
