package vn.com.hust.identityservice.services;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import vn.com.core.common.exceptions.AppException;
import vn.com.core.common.security.JwtProvider;
import vn.com.core.common.utils.DateUtil;
import vn.com.core.common.utils.StringUtil;
import vn.com.hust.admin.common.Constants;
import vn.com.hust.admin.data.CommonData;
import vn.com.hust.admin.model.SecSystemPolicy;
import vn.com.hust.admin.model.SecUser;
import vn.com.hust.admin.repository.SecSystemPolicyRepository;
import vn.com.hust.identityservice.data.ErrorCode;
import vn.com.hust.identityservice.data.LoginData;
import vn.com.hust.identityservice.data.TokenData;
import vn.com.hust.identityservice.data.UserData;
import vn.com.hust.identityservice.message.request.LoginRequest;
import vn.com.hust.identityservice.repository.SecUserRepository;
import vn.com.hust.identityservice.repository.SiteMapExRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public abstract class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    SecUserRepository secUserRepository;

    @Autowired
    SiteMapExRepository siteMapExRepository;

    @Autowired
    SecSystemPolicyRepository secSystemPolicyRepository;

    // @Cacheable(value = "userData", key = "#username")
    public abstract UserData findUser(String username);

    //hanx chuyen shop -> org
    public abstract Long getDeptId(Authentication authentication);

    public abstract Set<String> getListGroup(Authentication authentication);

    public abstract String getUserId(Authentication authentication);

    //public abstract String getListShopCode(Authentication authentication); // hanx khong dung nua

    public abstract void updateFailAttempts(String username, SecSystemPolicy policyLockTime, String appCode);

    public abstract void logWhenLogin(Authentication authentication, String ipAddress, Long status, String userId);

    public abstract void resetFailAttempts(String username);

    public CommonData login(LoginRequest loginRequest) {
        return CommonData.builder()
                .code(Constants.SUCCESSFUL)
                .message("")
                .timeOut("")
                .body((""))
                .build();
    }

    public TokenData refresh(String refreshToken, String accessToken) {
        String rawToken = jwtProvider.getJwtRaw(accessToken);
        String userName = jwtProvider.getUserNameFromTokenRSA(rawToken);
        String mainId = jwtProvider.getMainIdFromTokenRSA(rawToken);
        long userId = Long.parseLong(jwtProvider.getUserIdFromTokenRSA(rawToken));

        log.debug("Request refresh for user: {}", userName);
        log.debug("Request refresh for user: {}", userId);
        //validate token
        boolean isValidRefreshToken = jwtProvider.validateJwtToken(refreshToken);
        if (!isValidRefreshToken) {
            throw new AppException("Refresh token invalid, please try login");
        }

        // generate new token data
        TokenData newTokenData = jwtService.generateTokenSocial(userName, userId, mainId);

        // update userprinciple with new accesstoken
        newTokenData.setAccessTokenExpirationSecond(jwtService.getJwtExpiration());
        newTokenData.setRefreshTokenExpirationSecond(jwtService.getJwtRefreshExpiration());

        return newTokenData;
    }

    //hanx chuyen shop -> org
    public TokenData refreshChangeOrg(String refreshToken, String accessToken, Long newdeptId) {
        String rawToken = jwtProvider.getJwtRaw(accessToken);
        String userName = jwtProvider.getUserNameFromTokenRSA(rawToken);
        String olddeptId = jwtProvider.getdeptIdFromTokenRSA(rawToken); //hanx chuyen shop -> org
        long userId = Long.valueOf(jwtProvider.getUserIdFromTokenRSA(rawToken));
        ArrayList<String> groups = jwtProvider.getLstGroupFromTokenRSA(rawToken);

        log.debug("Request refresh for user: {}", userName);
        log.debug("Request refresh for user old org : {}", olddeptId);
        log.debug("Request refresh for user new org : {}", newdeptId);
        log.debug("Request refresh for user: {}", userId);
        log.debug("Request refresh for user: {}", groups);

        //validate token
        boolean isValidRefreshToken = jwtProvider.validateJwtToken(refreshToken);
        if (!isValidRefreshToken) {
            throw new AppException("Refresh token invalid, please try login");
        }

        // generate new token data
        TokenData newTokenData = jwtService.generateToken(userName,
                userId, newdeptId, new HashSet<String>(groups));

        // update userprinciple with new accesstoken
        newTokenData.setAccessTokenExpirationSecond(jwtService.getJwtExpiration());
        newTokenData.setRefreshTokenExpirationSecond(jwtService.getJwtRefreshExpiration());

        return newTokenData;
    }

    //hanx chuyen shop -> org
    public void logout(String token) {
        String rawToken = jwtProvider.getJwtRaw(token);
        String userName = jwtProvider.getUserNameFromTokenRSA(rawToken);
        String deptId = jwtProvider.getdeptIdFromTokenRSA(rawToken); //hanx chuyen shop -> org
        String userId = jwtProvider.getUserIdFromTokenRSA(rawToken);

        log.debug("Request logout to app: from userid {}, user name {}, org code {},  ", userId, userName, deptId);
    }

    public CommonData getUserInfoAdmin(Long userId) {
        CommonData returnFail = CommonData.builder()
                .code(Constants.SUCCESSFUL)
                .message("")
                .body(null)
                .build();
        SecUser secUser = null;
        try {
            log.info("getUserInfoAdmin: " + userId);
            secUser = secUserRepository.findByUserId(userId);
        } catch (Exception e) {
//            e.printStackTrace();
            log.error(e.toString(), e);
            log.error("Account not exists");
            returnFail.setCode(Constants.ERROR_SYSTEM);
            returnFail.setMessage("Account not exists");
            return returnFail;
        }
        List<String> listGroup = siteMapExRepository.getGroupNameAccess(secUser.getUserId(), "ADMIN");
        Set<String> setGroup = new HashSet<>();
        if (listGroup != null && !listGroup.isEmpty()) {
            for (String s : listGroup) {
                setGroup.add(s);
            }
        }
        TokenData tokenData = null;
        try {
            tokenData = jwtService.generateToken(secUser.getUserName(),
                    secUser.getUserId(), secUser.getDeptId(), setGroup);
        } catch (AppException ex) {
            //throw ex;
//            ex.printStackTrace();
            log.error(ex.toString(), ex);
            returnFail.setCode(Constants.ERROR_SYSTEM);
            returnFail.setMessage("Có lỗi xảy ra");
            return returnFail;
        }
        SecSystemPolicy policyManagement =
                secSystemPolicyRepository.findByPolicyType("EXPIRATION_TIME_LOGIN_SESSION");
        SecSystemPolicy policyManagementWaiting = secSystemPolicyRepository.findByPolicyType("WAITING_TIME");
        String timeOut = null;
        if (policyManagement != null && !"".equals(StringUtil.nvl(policyManagement.getPolicyValue(), ""))) {
            timeOut = String.valueOf(Integer.parseInt(policyManagement.getPolicyValue()) * 60);
        }
        Integer waitingTime = null;
        if (policyManagementWaiting != null && !""
                .equals(StringUtil.nvl(policyManagementWaiting.getPolicyValue(), ""))) {
            waitingTime = Integer.parseInt(policyManagementWaiting.getPolicyValue());
        }
        String timeRefresh = null;
        double rate = 3;
        if (timeOut != null) {
            timeRefresh = String.valueOf(Math.round((Double.parseDouble(timeOut)) / rate) * 2);
        }
        long remainPasswordExpire = -1;
        SecSystemPolicy secSystemPolicy = secSystemPolicyRepository.findByPolicyType("PASSWORD_EXPIRE_DURATION");
        String passwordExpireDurationStr = secSystemPolicy.getPolicyValue();
        int passwordExpireDuration =
                passwordExpireDurationStr == null ? 0 : Integer.parseInt(passwordExpireDurationStr);
        if (passwordExpireDuration > 0 && !"2".equals(secUser.getPasswordExpireStatus())) {
            LocalDate now = LocalDate.now();
            LocalDate lastChangePassword =
                    toLocalDate(DateUtils.addDays(secUser.getLastChangePassword(), passwordExpireDuration));
            remainPasswordExpire = ChronoUnit.DAYS.between(now, lastChangePassword);
        }

        LoginData logindata = LoginData.builder().accessToken(tokenData.getAccessToken())
                .accessTokenExpirationSecond(Integer.valueOf(StringUtil.nvl(timeOut,
                        String.valueOf(
                                jwtService
                                        .getJwtExpiration()))))
                .refreshToken(tokenData.getRefreshToken())
                .refreshTokenExpirationSecond(Integer.valueOf(StringUtil.nvl(timeRefresh,
                        String.valueOf(
                                jwtService
                                        .getJwtRefreshExpiration()))))
                .username(secUser.getUserName())
                .deptId(secUser.getDeptId())
                .userId(secUser.getUserId()) //hanx chuyen shop -> org
                .waitingTime(waitingTime)
                //.listShopCode(getListShopCode(authentication)) // khong dung nua
                //.passwordExpiredStatus(
                //		"0".equals(((AdminUserPrinciple) authentication.getPrincipal()).getPasswordExpireStatus())
                //				? true
                //				: false)
                //.responsibleManyShop(((AdminUserPrinciple) authentication.getPrincipal()).getResponsibleManyShop()) // khong dung nua
                .remainExpirePassword(remainPasswordExpire)
                //.listAppId(listAppId) // them listAppId duoc phep truy cap
                .build();
        return CommonData.builder()
                .code(Constants.SUCCESSFUL)
                .message("")
                .body(logindata)
                .build();
    }

    public CommonData generateTokenExpiration100Year(LoginRequest loginRequest) {
        CommonData returnFail = CommonData.builder()
                .code(Constants.SUCCESSFUL)
                .message("")
                .body(null)
                .build();
        Authentication authentication = null;
        SecSystemPolicy policyManagement = null;
        SecSystemPolicy policyLockTime = null;
        try {
            if (!"MOBILE".equals(loginRequest.getAppCode())) {
                policyManagement = secSystemPolicyRepository.findByPolicyType("WEB_LOGIN_MANAGEMENT");
                if (policyManagement != null && "1".equals(StringUtil.nvl(policyManagement.getPolicyValue(), ""))) {
                    policyLockTime = secSystemPolicyRepository.findByPolicyType("LOCK_TIME_INCORRECT_INPUT");
                }
            }
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (!"MOBILE".equals(loginRequest.getAppCode())) {
                if (policyManagement != null && "1".equals(StringUtil.nvl(policyManagement.getPolicyValue(), ""))) {
                    if (policyLockTime != null && !"".equals(StringUtil.nvl(policyLockTime.getPolicyValue(), ""))) {
                        SecUser secUser = secUserRepository.findFirstByUserNameIgnoreCase(loginRequest.getUsername())
                                .orElseThrow(
                                        () -> new UsernameNotFoundException(
                                                "User Not Found with -> username or email : " + loginRequest
                                                        .getUsername()));
                        Date blockDate = secUser.getLastBlockDate();
                        if (secUser.getLastBlockDate() != null) {
                            if (DateUtil.addMinute(blockDate, Integer.parseInt(policyLockTime.getPolicyValue()))
                                    .after(new Date())) {
                                throw new BadCredentialsException("LOCK_MINUTE");
                            }
                            secUser.setLastBlockDate(null);
                            secUser.setLoginFailureCount(null);
                            secUserRepository.save(secUser);
                        }
                    }
                }
            }
        } catch (BadCredentialsException e) {
//            e.printStackTrace();
            log.error(e.toString(), e);
            log.error("Login failed username: {}", loginRequest.getUsername());
            try {
                updateFailAttempts(loginRequest.getUsername(), policyLockTime, loginRequest.getAppCode());
            } catch (LockedException lockedException) {
//                lockedException.printStackTrace();
                log.error(lockedException.toString(), lockedException);
                log.error("Account locked username: {" + loginRequest.getUsername() + "}");
                if (lockedException.getMessage().contains("LOCK_MINUTE")) {
                    returnFail.setCode(Constants.ERROR_SYSTEM);
                    returnFail.setMessage(ErrorCode.ERROR_012.replace("XXXXXX", policyLockTime.getPolicyValue()));
                } else {
                    returnFail.setCode(Constants.ERROR_SYSTEM);
                    returnFail.setMessage(ErrorCode.ERROR_003);
                }
                return returnFail;
            }
            if (e.getMessage().contains("LOCK_MINUTE")) {
                returnFail.setCode(Constants.ERROR_SYSTEM);
                returnFail.setMessage(ErrorCode.ERROR_012.replace("XXXXXX", policyLockTime.getPolicyValue()));
            } else {
                returnFail.setCode(Constants.ERROR_SYSTEM);
                returnFail.setMessage(ErrorCode.ERROR_009);
            }
            return returnFail;
        } catch (LockedException e) {
//            e.printStackTrace();
            log.error(e.toString(), e);
            log.error("Account locked username: {" + loginRequest.getUsername() + "}");
            //throw e;
            returnFail.setCode(Constants.ERROR_SYSTEM);
            returnFail.setMessage(ErrorCode.ERROR_003);
            return returnFail;
        } catch (AppException e) {
//            e.printStackTrace();
            log.error(e.getMessage(), e);
            //throw e;
            returnFail.setCode(Constants.ERROR_SYSTEM);
            returnFail.setMessage(e.getMessage());
            return returnFail;
        } catch (UsernameNotFoundException e) {
//            e.printStackTrace();
            log.error(e.toString(), e);
            log.error(e.getMessage(), loginRequest.getUsername());
            returnFail.setCode(Constants.ERROR_SYSTEM);
            returnFail.setMessage(ErrorCode.ERROR_009);
            return returnFail;
        } catch (Exception e) {
//            e.printStackTrace();
            log.error(e.toString(), e);
            log.error(e.getMessage(), loginRequest.getUsername());
            returnFail.setCode(Constants.ERROR_SYSTEM);
            returnFail.setMessage(ErrorCode.ERROR_999);
            return returnFail;
        }
        TokenData tokenData = null;
        try {
            tokenData = jwtService
                    .generateTokenExpiration100Year(((UserDetails) authentication.getPrincipal()).getUsername(),
                            getUserId(authentication), getDeptId(authentication),
                            getListGroup(authentication));
        } catch (AppException ex) {
            //throw ex;
//            ex.printStackTrace();
            log.error(ex.toString(), ex);
            returnFail.setCode(Constants.ERROR_SYSTEM);
            returnFail.setMessage("Có lỗi xảy ra khi lấy token 100 năm");
            return returnFail;
        }
        return CommonData.builder()
                .code(Constants.SUCCESSFUL)
                .message("")
                .body(tokenData.getAccessToken())
                .build();
    }


    public CommonData getTokenByUserName(String userName) {
        CommonData returnFail = CommonData.builder()
                .code(Constants.SUCCESSFUL)
                .message("")
                .body(null)
                .build();
        SecUser secUser = null;
        try {
            secUser = secUserRepository.findFirstByUserNameIgnoreCase(StringUtil.nvl(userName, "").trim())
                    .orElseThrow(() -> new AppException("Not found"));
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("Account not exists", e);
            returnFail.setCode(Constants.ERROR_SYSTEM);
            returnFail.setMessage("Account not exists");
            return returnFail;
        }
        List<String> listGroup = siteMapExRepository.getGroupNameAccess(secUser.getUserId(), "ADMIN");
        Set<String> setGroup = new HashSet<>();
        if (listGroup != null && !listGroup.isEmpty()) {
            for (String s : listGroup) {
                setGroup.add(s);
            }
        }
        TokenData tokenData = null;
        try {
            tokenData = jwtService.generateToken(secUser.getUserName(),
                    secUser.getUserId(), secUser.getDeptId(), setGroup);
        } catch (AppException ex) {
            //throw ex;
//            ex.printStackTrace();
            log.error(ex.toString(), ex);
            returnFail.setCode(Constants.ERROR_SYSTEM);
            returnFail.setMessage("Có lỗi xảy ra");
            return returnFail;
        }
        LoginData logindata = LoginData.builder().accessToken(tokenData.getAccessToken())
                .accessTokenExpirationSecond(jwtService.getJwtExpiration())
                .refreshToken(tokenData.getRefreshToken())
                .refreshTokenExpirationSecond(jwtService.getJwtRefreshExpiration())
                .username(secUser.getUserName())
                .deptId(secUser.getDeptId())
                .userId(secUser.getUserId())
                .build();
        return CommonData.builder()
                .code(Constants.SUCCESSFUL)
                .message("")
                .body(logindata)
                .build();
    }



    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
