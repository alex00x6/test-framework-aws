package mechanics.system.aws;

import mechanics.system.aws.objects.AWSURI;
import mechanics.system.constant.AssembledUrls;
import mechanics.system.credentials.RoleSwitcher;
import mechanics.system.http.RequestSender;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


public class SignAWSv4 extends RequestSender {


    public Map<String, String> allHeaders(String method, String url) {
        Map<String, String> authHeaders = authHeaders(method, url);
        Map<String, String> headers = standardHeaders();

        Map<String, String> map3 = new HashMap<>();
        map3.putAll(authHeaders);
        map3.putAll(headers);
        return map3;
    }

    public Map<String, String> allHeaders(String method, String url, String body) {
        Map<String, String> authHeaders = authHeaders(method, url, body);
        Map<String, String> headers = standardHeaders();

        Map<String, String> map3 = new HashMap<>();
        map3.putAll(authHeaders);
        map3.putAll(headers);
        return map3;
    }

    public Map<String, String> authHeaders(String method, String url) {
        Date date = new Date();
        AWSURI awsuri = parseForCanonicalRequest(method, url);
        String sign = generateSign(date, awsuri);
        Map<String, String> map = new HashMap<>();
        String amzDate = getAmzDate(date);
        map.put("x-amz-date", amzDate);
        map.put("Authorization", sign);
        map.put("x-amz-security-token", RoleSwitcher.getCurrentUser().getAwsKeys().getSessionToken());
        return map;
    }

    public Map<String, String> authHeaders(String method, String url, String body) {
        Date date = new Date();
        AWSURI awsuri = parseForCanonicalRequest(method, url);
        awsuri.setPayload(body);
        String sign = generateSign(date, awsuri);
        Map<String, String> map = new HashMap<>();
        String amzDate = getAmzDate(date);
        map.put("x-amz-date", amzDate);
        map.put("Authorization", sign);
        map.put("x-amz-security-token", RoleSwitcher.getCurrentUser().getAwsKeys().getSessionToken());
        return map;
    }


    public Map<String, String> standardHeaders() {
        Map<String, String> map = new HashMap<>();
        map.put("Connection", "keep-alive");
        map.put("Cache-Control", "no-cache");
        map.put("Pragma", "no-cache");
        map.put("Referer", AssembledUrls.redirectClientURI + "/");
        map.put("Accept", "*/*");
        map.put("Content-Type", "application/json");
        map.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        map.put("Accept-Language", "en-US,en;q=0.8");
        map.put("Accept-Encoding", "gzip, deflate, sdch, br");
        map.put("Origin", AssembledUrls.redirectClientURI);

        return map;
    }

    private AWSURI parseForCanonicalRequest(String method, String url) {
        AWSURI awsuri = new AWSURI();
        URL uri = null;
        try {
            uri = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String host = uri.getHost();
        String path = uri.getPath();
        String queryString = "";
        if (uri.getQuery() != null) {
            queryString = uri.getQuery();
        }

        String serviceName = null;
        String regionName = null;

        if (uri.getHost().contains(".execute-api.")) {
            serviceName = "execute-api";
        }
        if (uri.getHost().contains(".es.")) {
            serviceName = "es";
        }


        if (uri.getHost().contains(".us-east-1.")) {
            regionName = "us-east-1";
        }
        if (uri.getHost().contains(".us-east-2.")) {
            regionName = "us-east-2";
        }
        if (uri.getHost().contains(".us-west-1.")) {
            regionName = "us-west-1";
        }
        if (uri.getHost().contains(".us-west-2.")) {
            regionName = "us-west-2";
        }
        if (uri.getHost().contains(".eu-central-1.")) {
            regionName = "eu-central-1";
        }
        if (uri.getHost().contains(".eu-west-1.")) {
            regionName = "eu-west-1";
        }
        if (uri.getHost().contains(".eu-west-2.")) {
            regionName = "eu-west-2";
        }


        if (serviceName == null || regionName == null) {
            System.out.println("Looks like you using unknown URL, check it!");
            if (serviceName == null) {
                System.out.println("ERR: Unknown amazon service name");
            }
            if (regionName == null) {
                System.out.println("ERR: Unknown amazon region name");
            }
        }


        awsuri.setMethod(method);
        awsuri.setCanonicalUri(path);
        awsuri.setCanonicalQueryString(queryString);
        awsuri.setHost(host);
        awsuri.setServiceName(serviceName);
        awsuri.setRegionName(regionName);
        awsuri.setFullURL(url);

        return awsuri;
    }


    private String generateSign(Date date, AWSURI awsuri) {
        String method = awsuri.getMethod();
        String serviceName = awsuri.getServiceName();
        String regionName = awsuri.getRegionName();
        String host = awsuri.getHost();
        String canonicalUri = awsuri.getCanonicalUri();
        String canonicalQueryString = awsuri.getCanonicalQueryString();

        String signedHeaders = "host;x-amz-date";
        String algoritm = "AWS4-HMAC-SHA256";
        String access_key_id = RoleSwitcher.getCurrentUser().getAwsKeys().getAccessKeyId();
        String secret_access_key = RoleSwitcher.getCurrentUser().getAwsKeys().getSecretAccessKey();

        String amzDate = getAmzDate(date);
        String dateStamp = getDateStamp(date);

        String credentialScope = dateStamp + "/" + regionName + "/" + serviceName + "/" + "aws4_request";
        String canonicalHeaders = "host:" + host + "\n" + "x-amz-date:" + amzDate + "\n";
        String payloadHash = SHA256("");
        if (awsuri.getPayload() != null) {
            payloadHash = SHA256(awsuri.getPayload());
        }
        String canonicalRequest = method + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + signedHeaders + "\n" + payloadHash;

        byte[] signingKey = new byte[0];
        try {
            signingKey = getSignatureKey(secret_access_key, dateStamp, regionName, serviceName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String stringToSign = algoritm + "\n" + amzDate + "\n" + credentialScope + "\n" + SHA256(canonicalRequest);

        byte[] si = new byte[0];
        try {
            si = HmacSHA256(stringToSign, signingKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String signature = String.format("%064x", new java.math.BigInteger(1, si));
        String authorizationHeader = algoritm + " " + "Credential=" + access_key_id + "/" + credentialScope + ", " + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature;

        return authorizationHeader;
    }

    private String getAmzDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        f.setTimeZone(TimeZone.getTimeZone("GMT"));
        String xAmzDate = f.format(date);
        return xAmzDate;
    }

    private String getDateStamp(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        f.setTimeZone(TimeZone.getTimeZone("GMT"));
        String dateStamp = f.format(date);
        return dateStamp;
    }

    private byte[] HmacSHA256(String data, byte[] key) throws Exception {
        String algorithm = "HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data.getBytes("UTF8"));
    }

    private String SHA256(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            md.update(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest();
        String result = String.format("%064x", new java.math.BigInteger(1, digest));
        return result;
    }

    private byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws Exception {
        byte[] kSecret = ("AWS4" + key).getBytes("UTF8");
        byte[] kDate = HmacSHA256(dateStamp, kSecret);
        byte[] kRegion = HmacSHA256(regionName, kDate);
        byte[] kService = HmacSHA256(serviceName, kRegion);
        byte[] kSigning = HmacSHA256("aws4_request", kService);
        return kSigning;
    }
}