package mechanics.system.mqtt;


import com.amazonaws.services.iot.client.*;
import mechanics.system.constant.AssembledUrls;
import mechanics.system.credentials.RoleSwitcher;
import mechanics.system.credentials.User;
import mechanics.system.mqtt.pubSub.TestTopicListener;
import org.testng.Assert;

import java.util.concurrent.ThreadLocalRandom;

public class MQTTConnector {
    private final int connectionRetries = 2;
    private String clientEndpoint = AssembledUrls.iotEndpoint;
    private AWSIotMqttClient awsIotClient;

    public void mqttSubscribe(int openConnectionTimeMs, String topic) {
        String clientId = ThreadLocalRandom.current().nextInt(10, 999999 + 1) + "ex";
        String awsAccessKeyId = RoleSwitcher.getCurrentUser().getAwsKeys().getAccessKeyId();
        String awsSecretAccessKey = RoleSwitcher.getCurrentUser().getAwsKeys().getSecretAccessKey();
        String sessionToken = RoleSwitcher.getCurrentUser().getAwsKeys().getSessionToken();

        AWSIotMqttClient awsIotClient;
        if (sessionToken == null || sessionToken.equals("") || sessionToken.isEmpty()) {
            System.out.println("Launching IoT client without session token.");
            System.out.println("=========================");
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey);
        } else {
            System.out.println("Launching IoT client using session token.");
            System.out.println("=========================");
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, sessionToken);
        }


        awsIotClient.setConnectionTimeout(openConnectionTimeMs);
        awsIotClient.setKeepAliveInterval(openConnectionTimeMs);
        awsIotClient.setMaxConnectionRetries(connectionRetries);

        try {
            awsIotClient.connect();
        } catch (AWSIotException e) {
            e.printStackTrace();
        }

        AWSIotTopic topicIoT = new TestTopicListener(topic, AWSIotQos.QOS0);

        try {
            awsIotClient.subscribe(topicIoT);
            System.out.println("Subscriber: " + awsIotClient.getConnectionStatus().toString());
        } catch (AWSIotException e) {
            e.printStackTrace();
        }

        if (awsIotClient.getConnectionStatus().equals(AWSIotConnectionStatus.CONNECTED)) {
            sleep(openConnectionTimeMs);
            try {
                awsIotClient.unsubscribe(topic);
                awsIotClient.disconnect();
            } catch (AWSIotException e) {
                e.printStackTrace();
            }
        }
    }

    public void mqttPublish(String topic, String payload) {
        String clientId = ThreadLocalRandom.current().nextInt(10, 999999 + 1) + "eP";
        String awsAccessKeyId = RoleSwitcher.getCurrentUser().getAwsKeys().getAccessKeyId();
        String awsSecretAccessKey = RoleSwitcher.getCurrentUser().getAwsKeys().getSecretAccessKey();
        String sessionToken = RoleSwitcher.getCurrentUser().getAwsKeys().getSessionToken();

        AWSIotMqttClient awsIotClient;
        if (sessionToken == null || sessionToken.equals("") || sessionToken.isEmpty()) {
            System.out.println("Launching IoT client without session token.");
            System.out.println("=========================");
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey);
        } else {
            System.out.println("Launching IoT client using session token.");
            System.out.println("=========================");
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, sessionToken);
        }

        awsIotClient.setMaxConnectionRetries(connectionRetries);

        try {
            if (awsIotClient.getConnectionStatus().equals(AWSIotConnectionStatus.DISCONNECTED)) {
                awsIotClient.connect();
            }
            awsIotClient.publish(topic, payload);
            if (awsIotClient.getConnectionStatus().equals(AWSIotConnectionStatus.CONNECTED)) {
                awsIotClient.disconnect();
            }
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
    }

    public void mqttConnect() {
        String clientId = ThreadLocalRandom.current().nextInt(10, 999999 + 1) + "eP";
        String awsAccessKeyId = RoleSwitcher.getCurrentUser().getAwsKeys().getAccessKeyId();
        String awsSecretAccessKey = RoleSwitcher.getCurrentUser().getAwsKeys().getSecretAccessKey();
        String sessionToken = RoleSwitcher.getCurrentUser().getAwsKeys().getSessionToken();

        if (sessionToken == null || sessionToken.equals("") || sessionToken.isEmpty()) {
            System.out.println("Launching IoT client without session token.");
            System.out.println("=========================");
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey);
        } else {
            System.out.println("Launching IoT client using session token.");
            System.out.println("=========================");
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, sessionToken);
        }

        awsIotClient.setMaxConnectionRetries(connectionRetries);

        try {
            if (awsIotClient.getConnectionStatus().equals(AWSIotConnectionStatus.DISCONNECTED)) {
                awsIotClient.connect();
            }
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
    }

    public void mqttConnect(User user) {
        String clientId = ThreadLocalRandom.current().nextInt(10, 999999 + 1) + "eP";
        String awsAccessKeyId = user.getAwsKeys().getAccessKeyId();
        String awsSecretAccessKey = user.getAwsKeys().getSecretAccessKey();
        String sessionToken = user.getAwsKeys().getSessionToken();

        if (sessionToken == null || sessionToken.equals("") || sessionToken.isEmpty()) {
            System.out.println("Launching IoT client without session token.");
            System.out.println("=========================");
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey);
        } else {
            System.out.println("Launching IoT client using session token.");
            System.out.println("=========================");
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, sessionToken);
        }

        awsIotClient.setMaxConnectionRetries(connectionRetries);

        try {
            if (awsIotClient.getConnectionStatus().equals(AWSIotConnectionStatus.DISCONNECTED)) {
                awsIotClient.connect();
            }
        } catch (AWSIotException e) {
            e.printStackTrace();
            awsIotClient = null;
        }
    }

    public void mqttPublishToConnected(String topic, String payload) {
        if (awsIotClient!=null&&awsIotClient.getConnectionStatus().equals(AWSIotConnectionStatus.CONNECTED)) {
            try {
                awsIotClient.publish(topic, payload);
            } catch (AWSIotException e) {
                e.printStackTrace();
            }
        }
        else{
            Assert.assertTrue(false);
        }
    }

    public void mqttDisconnect() {
        if (awsIotClient.getConnectionStatus().equals(AWSIotConnectionStatus.CONNECTED)) {
            try {
                awsIotClient.disconnect();
            } catch (AWSIotException e) {
                e.printStackTrace();
            }
        }
    }


    public void mqttPublish(String topic, String payload, int repeats) {
        for (int i = 0; i < repeats; i++) {
            mqttPublish(topic, payload);
        }
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
