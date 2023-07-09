package vn.com.hust.identityservice.services;


import vn.com.hust.admin.common.Constants;
import vn.com.hust.admin.data.CommonListData;
import vn.com.hust.admin.model.SecGroup;
import vn.com.hust.identityservice.data.SiteMapData;

import java.util.ArrayList;
import java.util.List;

public abstract class ResourceService {

    public abstract List<SiteMapData> getSiteMapDataList(String appCode, String token);

    public List<SiteMapData> getSiteMaps(String appCode, String token) {
        return getSiteMapDataList(appCode, token);
    }

    public abstract List<SecGroup> getListGroupByPositionUser(Long userId, String appCode);

    public abstract List<String> getListGroupAccessAPP(Long userId, String appCode);

    public CommonListData getGroupByPositionUser(Long userId, String appCode) {
        List<SecGroup> listGroupPosition = getListGroupByPositionUser(userId, appCode);
        List<String> listGroupAccess = getListGroupAccessAPP(userId, appCode);
        List<Object> listObj = new ArrayList<>();
        listObj.add(listGroupAccess);
        listObj.add(listGroupPosition);
        return CommonListData.builder()
                .code(Constants.SUCCESSFUL)
                .message("")
                .data(listObj)
                .build();
    }

    public abstract List<String> getSiteMapDataListHidden(String appCode, String token);

    public List<String> getSiteMapsHidden(String appCode, String token) {
        return getSiteMapDataListHidden(appCode, token);
    }
}
