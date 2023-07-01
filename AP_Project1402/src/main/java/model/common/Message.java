package model.common;

public class Message {
    private String senderUsername;
    private String msg;
    private String toWhom;

    public Message(String senderUsername, String msg, String toWhom) {
        this.senderUsername = senderUsername;
        this.msg = msg;
        this.toWhom = toWhom;
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
}
