package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.isv.access.common.util.HttpRequestHelper;
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
     * 获取一个群的信息
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权企业的AccessToken
     * @param chatId        群ID
     * https://pre-oapi.dingtalk.com/chat/get?access_token=8ad4e5b5aedb3697ae9caf2c79d7585d&chatid=chat217caa817b30e581b412f141583307e6
     * {"errcode":0,"errmsg":"ok","chat_info":{"owner":"lifeng.zlf","chatid":"chat217caa817b30e581b412f141583307e6","name":"群你妹","useridlist":["lifeng.zlf","043425659249","06654942081038711"]}}
     */
    public ServiceResult<String> getChat(String suiteKey, String corpId,String accessToken,String chatId) {
        try {
            String url = oapiDomain + "/chat/get?access_token=" + accessToken+"&chatid="+chatId;
            String sr = httpRequestHelper.doHttpGet(url);
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


