package mechanics.system.credentials;

import mechanics.system.aws.objects.AWSCredentials;

/**
 * Created by Alex Storm on 08.06.2017.
 */
public class User {
    private String role;
    private String email;
    private String password;
    private AWSCredentials awsKeys;
    private long credentialsReceiveTime;

    @Override
    public String toString() {
        return "User{" +
                "role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", awsKeys=" + awsKeys +
                ", credentialsReceiveTime=" + credentialsReceiveTime +
                '}';
    }

    public String getRole() {
        return role;
    }

    void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public AWSCredentials getAwsKeys() {
        return awsKeys;
    }

    public void setAwsKeys(AWSCredentials awsKeys) {
        this.awsKeys = awsKeys;
    }

    public long getCredentialsReceiveTime() {
        return credentialsReceiveTime;
    }

    public void setCredentialsReceiveTime(long credentialsReceiveTime) {
        this.credentialsReceiveTime = credentialsReceiveTime;
    }

    public void setCredentialsReceiveTimeNow() {
        this.credentialsReceiveTime = System.currentTimeMillis();
    }
}
