package vn.com.hust.identityservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.core.common.exceptions.AppException;
import vn.com.core.common.security.JwtProvider;
import vn.com.core.common.utils.StringUtil;
import vn.com.hust.admin.model.CurrentUserData;
import vn.com.hust.admin.model.SecSystemPolicy;
import vn.com.hust.admin.repository.SecSystemPolicyRepository;
import vn.com.hust.identityservice.data.ErrorCode;
import vn.com.hust.identityservice.data.TokenData;

import java.util.Set;

@Service
@Slf4j
public class JwtService {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PermissionService permissionService;

    @Autowired
    SecSystemPolicyRepository secSystemPolicyRepository;

    public TokenData generateToken(String username, Long userId, Long deptId, Set<String> groups) throws AppException {
//        CommonControllerRequest commonControllerRequest = new CommonControllerRequest();
//        commonControllerRequest.setUsername(username);
//        commonControllerRequest.setDeptId(deptId);
        log.info("generateToken: username-" + username + "-userId:" + userId + "-deplId:" + deptId + "-group:" + groups
                .toString());
        CurrentUserData currentUserData = permissionService.getCurrentUserData(username, deptId);

        log.info("currentUserData: " + StringUtil.objectToJson(currentUserData));
        if (currentUserData == null || currentUserData.isEmpty()) {
            throw new AppException("ERROR_011", ErrorCode.ERROR_011);
        }
        SecSystemPolicy policyManagement = secSystemPolicyRepository.findByPolicyType("EXPIRATION_TIME_LOGIN_SESSION");
        String timeOut = null;
        if (policyManagement != null && !"".equals(StringUtil.nvl(policyManagement.getPolicyValue(), ""))) {
            timeOut = String.valueOf(Integer.parseInt(policyManagement.getPolicyValue()) * 60);
        }
        String accessToken = jwtProvider.generateAccessTokenRSA(username, userId, currentUserData.getOrgId(),
                currentUserData.getDeptId(), groups, timeOut, null);
        String timeRefresh = null;
        double rate = 3;
        if (timeOut != null) {
            timeRefresh = String.valueOf(Math.round((Double.parseDouble(timeOut)) / rate) * 2);
        }
        String refreshToken = jwtProvider.generateRefreshToken(accessToken, timeRefresh);
        return TokenData.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public TokenData generateToken2(String username, Long userId) throws AppException {
        log.info("generateToken: username-" + username + "-userId:" + userId.toString());
        SecSystemPolicy policyManagement = secSystemPolicyRepository.findByPolicyType("EXPIRATION_TIME_LOGIN_SESSION");
        String timeOut = null;
        if (policyManagement != null && !"".equals(StringUtil.nvl(policyManagement.getPolicyValue(), ""))) {
            timeOut = String.valueOf(Integer.parseInt(policyManagement.getPolicyValue()) * 60);
        }
        String accessToken = jwtProvider.generateAccessTokenRSA2(username, userId, timeOut, null);
        String timeRefresh = null;
        double rate = 3;
        if (timeOut != null) {
            timeRefresh = String.valueOf(Math.round((Double.parseDouble(timeOut)) / rate) * 2);
        }
        String refreshToken = jwtProvider.generateRefreshToken(accessToken, timeRefresh);
        return TokenData.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public TokenData generateTokenSocial(String username, Long userId, String mainId) {
        SecSystemPolicy policyManagement = secSystemPolicyRepository.findByPolicyType("EXPIRATION_TIME_LOGIN_SESSION");
        String timeOut = null;
        if (policyManagement != null && !"".equals(StringUtil.nvl(policyManagement.getPolicyValue(), ""))) {
            timeOut = String.valueOf(Integer.parseInt(policyManagement.getPolicyValue()) * 60);
        }
        String accessToken = jwtProvider.generateAccessTokenRSA(username, userId, null, null, null, timeOut, mainId);
        String refreshToken = jwtProvider.generateRefreshToken(accessToken);

        int expire;
        try {
            expire = Integer.parseInt(policyManagement.getPolicyValue()) * 60;
        } catch (Exception ignored) {
            // Neu loi thi expire 7 ngay
            log.error("Loi lay thoi gian token trong SEC_SYSTEM_POLICY.EXPIRATION_TIME_LOGIN_SESSION cho app, " +
                    "thoi han mac dinh 7 ngay");
            expire = 10080;
        }
        return TokenData.builder()
                .accessToken(accessToken)
                .accessTokenExpirationSecond(expire)
                .refreshToken(refreshToken)
                .build();
    }

    public Integer getJwtExpiration() {
        return jwtProvider.getJwtExpiration();
    }

    public Integer getJwtRefreshExpiration() {
        return jwtProvider.getJwtRefreshExpiration();
    }

    public TokenData generateTokenExpiration100Year(String username, String userId, Long deptId, Set<String> groups)
            throws AppException {
//        CommonControllerRequest commonControllerRequest = new CommonControllerRequest();
//        commonControllerRequest.setUsername(username);
//        commonControllerRequest.setDeptId(deptId);
        CurrentUserData currentUserData = permissionService.getCurrentUserData(username, deptId);
        if (currentUserData == null || currentUserData.isEmpty()) {
            throw new AppException("ERROR_011", ErrorCode.ERROR_011);
        }
        String accessToken = jwtProvider
                .generateAccessTokenRSAExpiration100Year(username, userId, currentUserData.getOrgId(),
                        currentUserData.getDeptId(), groups);
        String refreshToken = jwtProvider.generateRefreshToken(accessToken);
        return TokenData.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public TokenData getTokenAPI(String username) {
        String accessToken = jwtProvider.getTokenAPI(username);
        String refreshToken = jwtProvider.generateRefreshToken(accessToken);
        return TokenData.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

}
