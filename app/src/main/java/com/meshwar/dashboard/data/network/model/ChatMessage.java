package com.meshwar.dashboard.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatMessage {

    @Expose
    @SerializedName("from_me")
    private boolean fromMe;
    @Expose
    @SerializedName("receiver_image")
    private String receiverImage;
    @Expose
    @SerializedName("created_at")
    private String created_at;
    @Expose
    @SerializedName("sender_image")
    private String senderImage;
    @Expose
    @SerializedName("receiver_name")
    private String receiverName;
    @Expose
    @SerializedName("sender_name")
    private String senderName;
    @Expose
    @SerializedName("created_time")
    private String createdTime;
    @Expose
    @SerializedName("sender_type")
    private String senderType;
    @Expose
    @SerializedName("sender_uuid")
    private String senderUuid;
    @Expose
    @SerializedName("receiver_type")
    private String receiverType;
    @Expose
    @SerializedName("receiver_uuid")
    private String receiverUuid;
    @Expose
    @SerializedName("content_type")
    private String contentType;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("uuid")
    private String uuid;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean getFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    public String getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(String receiverImage) {
        this.receiverImage = receiverImage;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getSenderUuid() {
        return senderUuid;
    }

    public void setSenderUuid(String senderUuid) {
        this.senderUuid = senderUuid;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverUuid() {
        return receiverUuid;
    }

    public void setReceiverUuid(String receiverUuid) {
        this.receiverUuid = receiverUuid;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
