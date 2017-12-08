package com.dingtalk.isv.access.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 企业群VO。和钉钉开放平台chat系列接口对应
 */
public class CorpChatVO implements Serializable {
    /**
     * 授权企业CorpId
     */
    private String corpId;
    /**
     * 群ID
     */
    private String chatId;
    /**
     * 群名称
     */
    private String chatName;
    /**
     * 群主UserId
     */
    private String ownerUserId;
    /**
     * 群成员UserId列表
     */
    private List<String> userIdlist;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public List<String> getUserIdlist() {
        return userIdlist;
    }

    public void setUserIdlist(List<String> userIdlist) {
        this.userIdlist = userIdlist;
    }

    @Override
    public String toString() {
        return "CorpChatVO{" +
                "corpId='" + corpId + '\'' +
                ", chatId='" + chatId + '\'' +
                ", chatName='" + chatName + '\'' +
                ", ownerUserId='" + ownerUserId + '\'' +
                ", userIdlist=" + userIdlist +
                '}';
    }
}
