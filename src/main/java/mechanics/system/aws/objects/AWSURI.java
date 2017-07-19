package mechanics.system.aws.objects;

public class AWSURI {
    private String method;
    private String serviceName;
    private String regionName;
    private String host;
    private String canonicalUri;
    private String canonicalQueryString;
    private String fullURL;
    private String payload;

    @Override
    public String toString() {
        return "AWSURI{" +
                "method='" + method + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", host='" + host + '\'' +
                ", canonicalUri='" + canonicalUri + '\'' +
                ", canonicalQueryString='" + canonicalQueryString + '\'' +
                ", fullURL='" + fullURL + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCanonicalUri() {
        return canonicalUri;
    }

    public void setCanonicalUri(String canonicalUri) {
        this.canonicalUri = canonicalUri;
    }

    public String getCanonicalQueryString() {
        return canonicalQueryString;
    }

    public void setCanonicalQueryString(String canonicalQueryString) {
        this.canonicalQueryString = canonicalQueryString;
    }

    public String getFullURL() {
        return fullURL;
    }

    public void setFullURL(String fullURL) {
        this.fullURL = fullURL;
    }
}
