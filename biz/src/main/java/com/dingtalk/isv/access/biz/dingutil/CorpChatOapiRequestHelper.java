package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.model.CorpChatVO;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.isv.access.common.util.HttpRequestHelper;
import com.dingtalk.open.client.api.model.corp.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开放平台企业发送会话相关的接口封装
 */
public class CorpChatOapiRequestHelper {
    private static Logger mainLogger = LoggerFactory.getLogger(CorpChatOapiRequestHelper.class);
    private static final Logger bizLogger = LoggerFactory.getLogger("HTTP_INVOKE_LOGGER");
    @Resource
    private HttpRequestHelper httpRequestHelper;
    private String oapiDomain;

    public String getOapiDomain() {
        return oapiDomain;
    }

    public void setOapiDomain(String oapiDomain) {
        this.oapiDomain = oapiDomain;
    }

    /**
     * 创建一个企业群聊天
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权企业的AccessToken
     * @param chatName      群名称
     * @param ownerUserId   群主UserId
     * @param userIdList    群成员UserId列表
     * @param showHistoryType   历史消息是否可见
     */
    public ServiceResult<String> createChat(String suiteKey, String corpId,String accessToken,String chatName, String ownerUserId,List userIdList,Integer showHistoryType) {
        try {

            String url = oapiDomain + "/chat/create?access_token=" + accessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("name", chatName);
            parmMap.put("owner", ownerUserId);
            parmMap.put("useridlist", userIdList);
            parmMap.put("showHistoryType", showHistoryType);

            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                String chatId = jsonObject.getString("chatid");
                return ServiceResult.success(chatId);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }

    /**
     * 获取一个企业群的基本信息
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权企业的AccessToken
     * @param chatId        群ID
     */
    public ServiceResult<CorpChatVO> getChat(String suiteKey, String corpId, String accessToken, String chatId) {
        try {
            String url = oapiDomain + "/chat/get?access_token=" + accessToken+"&chatid="+chatId;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                CorpChatVO corpChatVO = new CorpChatVO();
                JSONObject chatObj = jsonObject.getJSONObject("chat_info");
                corpChatVO.setChatId(chatId);
                corpChatVO.setChatName(chatObj.getString("name"));
                corpChatVO.setOwnerUserId(chatObj.getString("owner"));
                corpChatVO.setUserIdlist(JSONArray.parseArray(chatObj.getJSONArray("useridlist").toJSONString(),String.class));
                return ServiceResult.success(corpChatVO);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }


    /**
     * 向企业群中发送消息
     * @param suiteKey      套件Key
     * @param corpId        授权企业CorpId
     * @param accessToken   授权企业的AccessToken
     * @param chatId        群ID
     * @param chatType      消息类型
     * @param messageBody   消息结构体
     * @return
     */
    public ServiceResult<Void> sendChatMsg(String suiteKey, String corpId, String accessToken, String chatId,String chatType,MessageBody.OABody messageBody) {
        try {
            String url = oapiDomain + "/chat/send?access_token=" + accessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("chatid", chatId);
            parmMap.put("msgtype", chatType);
            parmMap.put(chatType, messageBody);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                return ServiceResult.success(null);
            }
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("accessToken", accessToken)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrCode());
        }
    }
}


