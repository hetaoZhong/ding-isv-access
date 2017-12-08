package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.DepartmentVO;
import com.dingtalk.open.client.api.model.corp.DepartmentDetail;

/**
 * Created by mint on 16-7-17.
 */
public class DepartmentHelper {

    public static DepartmentVO DepartmentDetail2DepartmentVO(DepartmentDetail departmentDetail){
        if(null==departmentDetail){
            return null;
        }
        DepartmentVO departmentVO = new DepartmentVO();
        departmentVO.setDeptHiding(departmentDetail.getDeptHiding());
        departmentVO.setDeptManagerUseridList(departmentDetail.getDeptManagerUseridList());
        departmentVO.setDeptPerimits(departmentDetail.getDeptPerimits());
        departmentVO.setOrgDeptOwner(departmentDetail.getOrgDeptOwner());
        departmentVO.setOrder(departmentDetail.getOrder());
        return departmentVO;
    }

}
