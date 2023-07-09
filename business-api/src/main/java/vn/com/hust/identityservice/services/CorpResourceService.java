package vn.com.hust.identityservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.core.common.security.JwtProvider;
import vn.com.core.common.utils.DataUtil;
import vn.com.hust.admin.model.SecGroup;
import vn.com.hust.identityservice.data.SiteMapData;
import vn.com.hust.identityservice.model.SiteMapEx;
import vn.com.hust.identityservice.repository.SiteMapExRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorpResourceService extends ResourceService {
    private static final Logger log = LoggerFactory.getLogger(ResourceService.class);

    @Autowired
    SiteMapExRepository siteMapExRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public List<SiteMapData> getSiteMapDataList(String appCode, String token) {
        String rawToken = jwtProvider.getJwtRaw(token);
        String userId = jwtProvider.getUserIdFromTokenRSA(rawToken);
        List<SiteMapEx> siteMapExList = siteMapExRepository.getListSiteMapExByUserIdAndAppCode(userId, appCode);
        List<SiteMapData> siteMapDataList = DataUtil.parseToTree(siteMapExList).stream().map(x -> SiteMapData.from((SiteMapEx) x, appCode)).collect(Collectors.toList());

        log.debug("Request get sitemap from user: {}", jwtProvider.getUserNameFromTokenRSA(rawToken));
        return siteMapDataList;
    }

    @Override
    public List<SecGroup> getListGroupByPositionUser(Long userId, String appCode) {
        return siteMapExRepository.getListGroupByUserPosition(userId, appCode);
    }

    @Override
    public List<String> getListGroupAccessAPP(Long userId, String appCode) {
        return siteMapExRepository.getGroupNameAccess(userId, appCode);
    }

    @Override
    public List<String> getSiteMapDataListHidden(String appCode, String token) {
        String rawToken = jwtProvider.getJwtRaw(token);
        String userId = jwtProvider.getUserIdFromTokenRSA(rawToken);
        List<String> siteMapExList = siteMapExRepository.getListSiteMapHiddenByUserIdAndAppCode(userId, appCode);
        log.debug("Request get sitemap hidden from user: {}", jwtProvider.getUserNameFromTokenRSA(rawToken));
        return siteMapExList;
    }
}
