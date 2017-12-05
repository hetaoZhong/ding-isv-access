package com.dingtalk.isv.access.biz.helper;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.enums.suite.CorpCallBackTypeEnum;
import com.dingtalk.isv.access.api.model.ISVSSOTokenVO;
import com.dingtalk.isv.access.api.model.corp.OALoginUserVO;
import com.dingtalk.isv.access.api.model.suite.CorpSuiteCallBackVO;
import com.dingtalk.isv.access.api.model.suite.SuiteVO;
import com.dingtalk.isv.access.api.service.corp.CorpManageService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dingutil.CorpOapiRequestHelper;
import com.dingtalk.isv.access.biz.dingutil.ISVRequestHelper;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by lifeng.zlf on 2017/12/1.
 */
public class CorpOapiRequestHelperTest extends BaseTestCase {
    @Resource
    private ISVRequestHelper isvRequestHelper;
    @Resource
    private CorpOapiRequestHelper corpOapiRequestHelper;
    @Resource
    private SuiteManageService suiteManageService;
    @Resource
    private CorpManageService corpManageService;
    @Test
    public void test(){
        String corpId = "ding9f50b15bccd16741";
        String corpSecret = "WgqUzoTORc94jyEyZdM_RttJFdykSUlNPFEFJmix860a8LEv0o4IwtRYrROnBR_5";
        String suiteKey = "suitexdhgv7mn5ufoi9ui";
        String corpToken = corpManageService.getCorpToken(suiteKey,corpId).getResult().getCorpToken();
        ISVSSOTokenVO isvSSOTokenVO =  isvRequestHelper.getSSOToken(corpId,corpSecret).getResult();
        System.err.println("ssoToken::::"+ JSON.toJSONString(isvSSOTokenVO));
        System.err.println(isvSSOTokenVO.getExpiredTime());
        ServiceResult<CorpSuiteCallBackVO> sr = corpOapiRequestHelper.getCorpSuiteCallback(suiteKey,corpId,corpToken);
        System.err.println(JSON.toJSONString(sr));
        SuiteVO suiteVO = suiteManageService.getSuiteByKey(suiteKey).getResult();
        ServiceResult<Void> updateSr = corpOapiRequestHelper.updateCorpCallback(suiteKey,corpId,corpToken,suiteVO.getToken(),suiteVO.getEncodingAesKey(),"http://baidu.com", CorpCallBackTypeEnum.getAllTag());
        System.err.println(JSON.toJSONString(updateSr));
        sr = corpOapiRequestHelper.getCorpSuiteCallback(suiteKey,corpId,corpToken);
        System.err.println(JSON.toJSONString(sr));
    }
}
