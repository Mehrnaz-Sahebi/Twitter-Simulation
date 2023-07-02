package model.common;

import java.io.Serializable;

public class Message implements Serializable {
    private String senderUsername;
    private String msg;
    private String avtar;
    private String toWhom;

    public Message(String senderUsername, String msg, String toWhom, String avatar) {
        this.senderUsername = senderUsername;
        this.msg = msg;
        this.toWhom = toWhom;
        this.avtar = avatar;
    }

    public String getAvtar() {
        return avtar;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getMsg() {
        return msg;
    }

    public String getToWhom() {
        return toWhom;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setToWhom(String toWhom) {
        this.toWhom = toWhom;
    }

    public void setAvtar(String avtar) {
        this.avtar = avtar;
    }
}
