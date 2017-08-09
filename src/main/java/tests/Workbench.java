package tests;


import mechanics.api.ListenerAPI;
import mechanics.api.RequestManagerAPI;
import mechanics.system.constant.AssembledEquipments;
import mechanics.system.constant.StatusICT;
import mechanics.system.equipment.PayloadICT;
import mechanics.system.equipment.PayloadICTEmulator;
import mechanics.system.equipment.TriggerICT;
import mechanics.system.equipment.TriggerICTEmulator;
import mechanics.system.http.RequestSender;
import mechanics.system.mqtt.MQTTConnector;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Created by user on 19.04.2017.
 */
//@Listeners(ListenerAPI.class)
//@Listeners(ListenerUi.class)
public class Workbench {

//    @Test
    public void test(){

        String payload = PayloadICTEmulator.newBuilder()
                .setSpeedMs(10000)
                .setSlotCount(16)
                .setUserAccessLevel("Operator")
                .setOperatorName("Polina")
                .setStatus(StatusICT.UNREACHABLE.getValue())
                .setAlarmPercentage(0)
                .setSlotErrorPercentage(5)
                .setSlotNErrorAdjustment(0.5)
                .setMachineErrorPercentage(0)
                .setSlotStateErrorPercentage(2)
                .setSeed(0)
                .initialize();
        System.out.println(payload);

    }

//    @Test
    public void test2(){
        TriggerICTEmulator triggerICTEmulator = new TriggerICTEmulator();
        triggerICTEmulator.triggerYield();
//        triggerICT.triggerStatusUnknown();//это шото хз, на FGW: status:unknown, а в Ui:init//чтоб получить этот статус нужно отправить online
//        triggerICT.triggerStatusInit();//это шото не ок, на FGW: status:unknown, а в Ui:init //чтоб получить этот статус нужно отправить unknown
//        triggerICT.triggerStatusIdle();//это ок
//        triggerICT.triggerStatusWorking();//это ок
//        triggerICT.triggerStatusPause();//это ок
//        triggerICT.triggerStatusError();//это ок
//        triggerICT.triggerStatusUnreachable();//это шото хз, на FGW: status:unknown, а в Ui:init //чтоб получить этот статус нужно отправить offline

//        TriggerVPV triggerVPV = new TriggerVPV();
//        triggerVPV.triggerVPVAbnormalVibrationBlowerAbort();
//        triggerVPV.triggerVPVAbnormalVibrationMainAlarm();
//        triggerVPV.triggerVPVALLEXPLODED();

    }

//    @Test
    public void test3(){
        String payload = "{\n" +
                "  \"result\": {\n" +
                "    \"friendlyName\": \"6\",\n" +
                "    \"hasProduct\": true,\n" +
                "    \"id\": 6,\n" +
                "    \"maxTestTimeInSec\": 11,\n" +
                "    \"mode\": 2,\n" +
                "    \"productId\": \"X-21.1006\",\n" +
                "    \"state\": 6,\n" +
                "    \"testResult\": 3,\n" +
                "    \"testStartAt\": \"2017-07-28T12:12:00Z\"\n" +
                "  },\n" +
                "  \"resultTime\": \"2017-07-28T14:39:30Z\",\n" +
                "  \"Datastream\": {\n" +
                "    \"@iot.id\": 3135\n" +
                "  }\n" +
                "}";
        MQTTConnector mqttConnector = new MQTTConnector();
        mqttConnector.mqttConnect();
        for (int i = 0; i < 300; i++) {
            mqttConnector.mqttPublishToConnected("Data/IoT/EC2/Test/Auto/RBT/v1.0/Datastreams(3135)", payload);
        }
        mqttConnector.mqttDisconnect();
    }

//    @Test
    public void test4() {
        TriggerICT triggerICT = new TriggerICT();
        triggerICT.triggerSMTH();
    }

//    @Test
    public void test5(){
        RequestManagerAPI requestManagerAPI = new RequestManagerAPI();
        requestManagerAPI.hardDeleteUser("kov.iot.qa@gmail.com", "regular");
    }

//    @Test
    public void testNs(){
        String url = "https://a2awmps9ermju9.iot.us-east-1.amazonaws.com/things/Thing-90000/shadow";
        String method = "GET";
        RequestSender requestSender = new RequestSender();
        System.out.println(requestSender.sendAmazonRequest(method, url).asString());
    }

//    @Test
    public void teestee(){
        String equip = "Thing-90000-0";
        RequestManagerAPI requestManagerAPI = new RequestManagerAPI();
        System.out.println(requestManagerAPI.getLastYieldPointValue(equip));
        System.out.println(requestManagerAPI.getYieldInPeriodValue(equip));
    }

//    @Test
    public void payload(){
        System.out.println(PayloadICT.newBuilder().setTestResult(3).setDatastreamId(AssembledEquipments.ictDatastream).initialize());
    }

    @Test
    public void trigger(){
        new TriggerICT().triggerYieldFade();
    }

}
