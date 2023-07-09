package vn.com.hust.identityservice.repository;


import vn.com.hust.admin.model.SecGroup;
import vn.com.hust.identityservice.model.SiteMapEx;

import java.util.List;

public interface SiteMapExRepository {

    List<SiteMapEx> getListSiteMapExByUserIdAndAppCode(String userId, String appCode);

    List<String> getGroupNameAccess(Long userId, String appCode);

    List<SecGroup> getListGroupByUserPosition(Long userId, String appCode);

    List<String> getListSiteMapHiddenByUserIdAndAppCode(String userId, String appCode);
}
