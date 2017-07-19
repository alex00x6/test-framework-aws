package mechanics.system.aws.objects;

/**
 * Created by user on 17.03.2017.
 */
public class AWSCredentials {
    private String accessKeyId; //required for request and for sign
    private String secretAccessKey; //required for sign
    private String sessionToken; //required for request

    @Override
    public String toString() {
        return "AWSCredentials{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", secretAccessKey='" + secretAccessKey + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
