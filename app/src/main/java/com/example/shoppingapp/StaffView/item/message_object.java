package com.example.shoppingapp.StaffView.item;

public class message_object {
    private String msgId, senderId, message, time;

    public message_object(String msgId, String senderId, String message, String time) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.message = message;
        this.time = time;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
