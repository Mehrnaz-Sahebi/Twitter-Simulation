package com.example.view.common.src;

import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SocketModel implements Serializable {
    public Api eventType;
    public ResponseOrErrorType message;
    public Object data;
    private String jwToken;

    public SocketModel(Api eventType, Object data) {

        this(eventType, null, data);
    }
    public SocketModel(Api eventType, Object data, String jwt){
        this.eventType = eventType;
        this.message=null;
        this.data = data;
        this.jwToken = jwt;
    }

    public SocketModel(Api eventType, ResponseOrErrorType message, Object data) {
        this.eventType = eventType;
        this.data = data;
        this.message = message;
        this.jwToken = null;
    }

    public SocketModel(Api eventType, ResponseOrErrorType message) {
        this.eventType = eventType;
        this.message = message;
        this.jwToken = null;
    }

    public void setMessage(ResponseOrErrorType message) {
        this.message = message;
    }

    public void setEventType(Api eventType) {
        this.eventType = eventType;
    }
    public void makeJwToken(String username , String secret){
        String JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        JSONObject payload = new JSONObject();
        try {
            payload.put("username",username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String signature = hmacSha256(encode(JWT_HEADER.getBytes()) + "." + encode(payload.toString().getBytes()), secret);
        jwToken = encode(JWT_HEADER.getBytes()) + "." + encode(payload.toString().getBytes()) + "." + signature;
    }
    public void setJwTokenToNull(){
        jwToken = null;
    }
    private static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
    private static String hmacSha256(String data, String secret) {
        try {
            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return encode(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            //Logger.getLogger(JWebToken.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    public boolean checkJwToken(String secret){
        if (jwToken == null){
            return true;
        }
        String[] parts = jwToken.split("\\.");

        try {
            JSONObject payload = new JSONObject(decode(parts[1]));
            JSONObject header = new JSONObject(decode(parts[0]));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String signature = parts[2];
        String headerAndPayloadHashed = hmacSha256(parts[0] + "." + parts[1],secret);
        if(!signature.equals(headerAndPayloadHashed)){
            return false;
        }
        return true;
    }

    public String getUsername(){
        if(jwToken ==null){
            return null;
        }
        String[] parts = jwToken.split("\\.");
        JSONObject payload = null;
        try {
            payload = new JSONObject(decode(parts[1]));
            return payload.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getJwToken() {
        return jwToken;
    }

    public void setJwToken(String jwToken) {
        this.jwToken = jwToken;
    }

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) data;
    }
}