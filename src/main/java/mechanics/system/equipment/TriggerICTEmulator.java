package mechanics.system.equipment;

import com.jayway.restassured.response.Response;
import mechanics.system.constant.AssembledEquipments;
import mechanics.system.constant.HTTPMethod;
import mechanics.system.constant.StatusICT;
import mechanics.system.http.RequestSender;

/**
 * Created by Alex Storm on 27.07.2017.
 */
public class TriggerICTEmulator {
    private static String url;

    public TriggerICTEmulator() {
        url = AssembledEquipments.ictFgwSimulator;
    }

    public void triggerStatusUnknown() {
        String payload = PayloadICTEmulator.newBuilder().setStatus(StatusICT.UNKNOWN.getValue()).initialize();
        new RequestSender().sendRequest(HTTPMethod.PUT.getValue(), url, payload);
    }

    public void triggerStatusInit() {
        String payload = PayloadICTEmulator.newBuilder().setStatus(StatusICT.INIT.getValue()).initialize();
        new RequestSender().sendRequest(HTTPMethod.PUT.getValue(), url, payload);
    }

    public void triggerStatusIdle() {
        String payload = PayloadICTEmulator.newBuilder().setStatus(StatusICT.IDLE.getValue()).initialize();
        Response response = new RequestSender().sendRequest(HTTPMethod.PUT.getValue(), url, payload);
    }

    public void triggerStatusWorking() {
        String payload = PayloadICTEmulator.newBuilder().setStatus(StatusICT.WORKING.getValue()).initialize();
        new RequestSender().sendRequest(HTTPMethod.PUT.getValue(), url, payload);
    }

    public void triggerStatusPause() {
        String payload = PayloadICTEmulator.newBuilder().setStatus(StatusICT.PAUSE.getValue()).initialize();
        new RequestSender().sendRequest(HTTPMethod.PUT.getValue(), url, payload);
    }

    public void triggerStatusError() {
        String payload = PayloadICTEmulator.newBuilder().setStatus(StatusICT.ERROR.getValue()).initialize();
        new RequestSender().sendRequest(HTTPMethod.PUT.getValue(), url, payload);
    }

    public void triggerStatusStopping() {
        String payload = PayloadICTEmulator.newBuilder().setStatus(StatusICT.STOPPING.getValue()).initialize();
        new RequestSender().sendRequest(HTTPMethod.PUT.getValue(), url, payload);
    }

    public void triggerStatusUnreachable() {
        String payload = PayloadICTEmulator.newBuilder().setStatus(StatusICT.UNREACHABLE.getValue()).initialize();
        new RequestSender().sendRequest(HTTPMethod.PUT.getValue(), url, payload);
    }


    public void triggerYield(){
        String payload = PayloadICTEmulator.newBuilder().setSlotErrorPercentage(100).initialize();
        new RequestSender().sendRequest(HTTPMethod.PUT.getValue(), url, payload);
    }
}

