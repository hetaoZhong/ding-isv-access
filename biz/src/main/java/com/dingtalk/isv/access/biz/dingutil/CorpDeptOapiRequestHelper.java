package com.dingtalk.isv.access.biz.dingutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.isv.access.api.model.DepartmentVO;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.isv.access.common.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 开放平台企业和部门相关的通讯录接口封装
 */
public class CorpDeptOapiRequestHelper {
    private static Logger mainLogger = LoggerFactory.getLogger(CorpDeptOapiRequestHelper.class);
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
     * 获取企业管理员
     * @param suiteKey      套件SuiteKey
     * @param corpId        授权企业的CorpId
     * @param accessToken   授权企业的AccessToken
     * @param parentDeptId  父部门ID
     */
    public ServiceResult<List<Long>> getSubDeptIdList(String suiteKey, String corpId, String accessToken,Long parentDeptId) {
        try {
            String url = oapiDomain + "/department/list_ids?access_token=" + accessToken+"&id="+parentDeptId;
            String sr = httpRequestHelper.doHttpGet(url);
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
                List<Long> subDeptIdList = new ArrayList<Long>();
                JSONArray jsonArray = jsonObject.getJSONArray("sub_dept_id_list");
                if(!CollectionUtils.isEmpty(jsonArray)){
                    subDeptIdList = JSONArray.parseArray(jsonArray.toJSONString(),Long.class);
                }
                return ServiceResult.success(subDeptIdList);
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


