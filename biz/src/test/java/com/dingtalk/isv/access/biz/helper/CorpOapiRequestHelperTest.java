package com.dingtalk.isv.access.biz.helper;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.CorpTokenVO;
import com.dingtalk.isv.access.api.model.DingDepartmentVO;
import com.dingtalk.isv.access.api.service.CorpManageService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dingutil.CorpChatOapiRequestHelper;
import com.dingtalk.isv.access.biz.dingutil.CorpDeptOapiRequestHelper;
import com.dingtalk.isv.access.biz.dingutil.CorpOapiRequestHelper;
import com.dingtalk.isv.access.biz.dingutil.ISVRequestHelper;
import com.dingtalk.isv.access.common.model.ServiceResult;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private CorpDeptOapiRequestHelper corpDeptOapiRequestHelper;
    @Resource
    private CorpChatOapiRequestHelper corpChatOapiRequestHelper;
    @Test
    public void test(){
        String corpId = "ding9f50b15bccd16741";
        String ssoCorpSecret = "WgqUzoTORc94jyEyZdM_RttJFdykSUlNPFEFJmix860a8LEv0o4IwtRYrROnBR_5";
        String corpSecret = "PFqsPcQ3uGNLWl2oEbm5AflaP2CRRK7OG5CXyEWEJ30RHeGkj4vHukilfnmgvCOV";
        String suiteKey = "suitexdhgv7mn5ufoi9ui";
        CorpTokenVO corpTokenVO = corpManageService.getCorpToken(suiteKey,corpId).getResult();
        System.err.println("corpTokenVO::::"+ JSON.toJSONString(corpTokenVO));
        /**
        ISVSSOTokenVO isvSSOTokenVO =  isvRequestHelper.getSSOToken(corpId,ssoCorpSecret).getResult();
        System.err.println("isvSSOTokenVO::::"+ JSON.toJSONString(isvSSOTokenVO));
        System.err.println(isvSSOTokenVO.getExpiredTime());
        ServiceResult<CorpSuiteCallBackVO> sr = corpOapiRequestHelper.getCorpSuiteCallback(suiteKey,corpId,corpTokenVO.getCorpToken());
        System.err.println(JSON.toJSONString(sr));
        SuiteVO suiteVO = suiteManageService.getSuiteByKey(suiteKey).getResult();
        ServiceResult<Void> updateSr = corpOapiRequestHelper.updateCorpCallback(suiteKey,corpId,corpTokenVO.getCorpToken(),suiteVO.getToken(),suiteVO.getEncodingAesKey(),"http://baidu.com", CorpCallBackTypeEnum.getAllTag());
        System.err.println(JSON.toJSONString(updateSr));
        sr = corpOapiRequestHelper.getCorpSuiteCallback(suiteKey,corpId,corpTokenVO.getCorpToken());
        System.err.println(JSON.toJSONString(sr));
        ServiceResult<List<Long>> subDeptListSr = corpDeptOapiRequestHelper.getSubDeptIdList(suiteKey,corpId,corpTokenVO.getCorpToken(),642185L);
        System.err.println("subDeptListSr::::"+JSON.toJSONString(subDeptListSr));
         **/
        CorpTokenVO isvCorpTokenVO =  isvRequestHelper.getCorpToken(corpId,corpSecret).getResult();
        System.err.println("isvCorpTokenVO::::"+ JSON.toJSONString(isvCorpTokenVO));
        List<String> chatUserList = new ArrayList<String>();
        chatUserList.add("lifeng.zlf");
        chatUserList.add("043425659249");
        chatUserList.add("06654942081038711");
        //ISV默认没有这个接口权限。只能用企业自己的TOKEN进行测试
        /**
        ServiceResult<String> createChatSr = corpChatOapiRequestHelper.createChat(suiteKey,corpId,isvCorpTokenVO.getCorpToken(),"群你妹","lifeng.zlf",chatUserList,1);
        System.err.println("createChatSr::::"+JSON.toJSONString(createChatSr));
        ServiceResult<CorpChatVO> chatSr = corpChatOapiRequestHelper.getChat(suiteKey,corpId,isvCorpTokenVO.getCorpToken(),createChatSr.getResult());
        System.err.println("chatSr::::"+JSON.toJSONString(chatSr));
        String chatId = "chat861bc18382f2cf4e782d22dbb9383c9a";
        MessageBody.OABody message = new MessageBody.OABody();
        MessageBody.OABody.Head head = new MessageBody.OABody.Head();
        MessageBody.OABody.Body body = new MessageBody.OABody.Body();
        head.setText("HEAD");
        head.setBgcolor("ffffa328");
        head.setText("客户详情");
        body.setContent("标题 \r\n 换行");
        body.setTitle("xxxxxxxx \r\n  换行");
        message.setHead(head);
        message.setBody(body);
        message.setMessage_url("http://taobao.com");
        ServiceResult<Void> sendSr = corpChatOapiRequestHelper.sendChatMsg(suiteKey,corpId,isvCorpTokenVO.getCorpToken(),chatId, MessageType.OA,message);
        System.err.println("sendSr::::"+JSON.toJSONString(sendSr));
        **/


        ServiceResult<DingDepartmentVO> departmentVOSr = corpDeptOapiRequestHelper.getDeptById(suiteKey,corpId,corpTokenVO.getCorpToken(),642185L);
        System.err.println("departmentVOSr::::"+JSON.toJSONString(departmentVOSr));
    }

}
