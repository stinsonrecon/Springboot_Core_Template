package vn.com.hust.identityservice.services;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.core.common.exceptions.AppException;
import vn.com.core.common.utils.DateUtil;
import vn.com.core.common.utils.StringUtil;
import vn.com.hust.admin.model.*;
import vn.com.hust.admin.repository.SecAccessLogRepository;
import vn.com.hust.admin.repository.SecSystemPolicyRepository;
import vn.com.hust.identityservice.data.*;
import vn.com.hust.identityservice.repository.SecUserRepository;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CorpAuthenticationService extends AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(CorpAuthenticationService.class);

    private static final String MAX_LOGIN_FAILURE = "MAX_LOGIN_FAILURE";
    private static final String PASSWORD_EXPIRE_DURATION = "PASSWORD_EXPIRE_DURATION";

    private static final String MAX_LOGIN_FAILURE_APP = "MAX_LOGIN_FAILURE_APP";

    @Autowired
    SecUserRepository secUserRepository;

    @Autowired
    SecSystemPolicyRepository secSystemPolicyRepository;

    @Autowired
    SecAccessLogRepository secAccessLogRepository;

    @Autowired
    AuthorizationService authorizationService;

    private static List<ScheduleData> buildScheduleDataList(List<SecGroup> secGroups) {
        List<ScheduleData> scheduleDataList = new ArrayList<>();

        for (SecGroup sg : secGroups) {
            List<SecSchedule> secSchedules = sg.getSecSchedule();
            for (SecSchedule ss : secSchedules) {
                if (ss.getStatus() == 1) {
                    List<SecScheduleDetail> SecScheduleDetails = ss.getSecScheduleDetail();
                    for (SecScheduleDetail ssd : SecScheduleDetails) {
                        ScheduleData scheduleData = ScheduleData.from(ssd);
                        scheduleDataList.add(scheduleData);
                    }
                }
            }
        }
        return scheduleDataList;
    }

    private static List<SiteMapData> sortTreeByOrd(List<SiteMapData> siteMapDataList) {
        List<SiteMapData> st = sortListByOrd(siteMapDataList);
        for (SiteMapData s : st) {
            if (s.getItems() != null && !s.getItems().isEmpty()) {
                s.setItems(sortListByOrd(s.getItems()));
                sortTreeByOrd(s.getItems());
            }
        }
        return st;
    }

    private static List<SiteMapData> sortListByOrd(List<SiteMapData> siteMapDataList) {
        return siteMapDataList.stream().sorted(Comparator.comparingInt(SiteMapData::getOrd))
                .collect(Collectors.toList());
    }

    private static List<SiteMapData> parseToTree(List<SiteMapData> lstData) {
        if (lstData == null || lstData.isEmpty()) {
            return new ArrayList<>();
        }

        List<SiteMapData> lTemp = lstData.stream().sorted(Comparator.comparingLong(SiteMapData::getId))
                .collect(Collectors.toList());

        List<SiteMapData> lstTreeData = new ArrayList<>();
        Map<Long, SiteMapData> mapTreeData = new HashMap<>();

        for (SiteMapData node : lTemp) {
            Long nodeId = node.getId();
            mapTreeData.put(nodeId, node);
            if (node.getParentId() == null) {
                lstTreeData.add(node);
                continue;
            }
            SiteMapData parentNode = mapTreeData.get(node.getParentId());
            if (parentNode != null) {
                parentNode.addChild(node);
            }
        }

        return lstTreeData;
    }

    @Override
    public UserData findUser(String username) {
        SecUser secUser = secUserRepository.findFirstByUserNameIgnoreCase(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));


        //check schedule
        UserData userData;

        userData = UserData.from(secUser);

        return userData;
    }

    //hanx chuyen shop -> org
    @Override
    public Long getDeptId(Authentication authentication) {
        return ((AdminUserPrinciple) authentication.getPrincipal()).getDeptId();
    }

    @Override
    public Set<String> getListGroup(Authentication authentication) {
        AdminUserPrinciple adminUserPrinciple = (AdminUserPrinciple) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> grantedAuthorityList = adminUserPrinciple.getAuthorities();
        Set<String> listGroup = new HashSet<>();
        for (GrantedAuthority g : grantedAuthorityList) {
            listGroup.add(g.getAuthority());
        }
        return listGroup;
    }

    @Override
    public String getUserId(Authentication authentication) {
        return ((AdminUserPrinciple) authentication.getPrincipal()).getUserId();
    }

    @Override
    public void updateFailAttempts(String username, SecSystemPolicy policyLockTime, String appCode) {
        SecUser secUser = secUserRepository.findFirstByUserNameIgnoreCase(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

        SecSystemPolicy secSystemPolicy = null;
        if (!"MOBILE".equals(appCode)) {
            secSystemPolicy = secSystemPolicyRepository.findByPolicyType(MAX_LOGIN_FAILURE);
        }
        int maxLoginFailure = 5;

        if (secSystemPolicy != null && secSystemPolicy.getPolicyValue() != null)
            maxLoginFailure = Integer.parseInt(secSystemPolicy.getPolicyValue());

        if (secUser.getLoginFailureCount() != null && secUser.getLoginFailureCount() >= maxLoginFailure) {
            if (policyLockTime != null && !"".equals(StringUtil.nvl(policyLockTime.getPolicyValue(), ""))) {
                secUser.setLastBlockDate(new Date());
                secUserRepository.save(secUser);
                long difference = Long.parseLong(policyLockTime.getPolicyValue()) * 60;
                throw new LockedException("LOCK_MINUTE" + difference);
            } else {
                secUser.setStatus(0L);
                secUser.setLastBlockDate(new Date());
                secUserRepository.save(secUser);
                throw new LockedException("Account locked");
            }
        }

        Long currentCount = secUser.getLoginFailureCount();

        if (currentCount == null)
            currentCount = 0L;
        if (!"MOBILE".equals(appCode) && (secSystemPolicy == null || secSystemPolicy.getPolicyValue() == null)) {

        } else {
            secUser.setLoginFailureCount(currentCount + 1);

            secUserRepository.save(secUser);
        }
    }

    @Override
    public void resetFailAttempts(String username) {
        SecUser secUser = secUserRepository.findFirstByUserNameIgnoreCase(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
        secUser.setLastBlockDate(null);
        secUser.setLoginFailureCount(null);
        secUserRepository.save(secUser);
    }

    @Override
    public void logWhenLogin(Authentication authentication, String ipAddress, Long status, String userId) {
        if (Objects.nonNull(authentication)) {
            AdminUserPrinciple adminUserPrinciple = (AdminUserPrinciple) authentication.getPrincipal();
            userId = adminUserPrinciple.getUserId();
        }
        SecAccessLog secAccessLog = SecAccessLog.builder().accessDate(new Date()).ipAddress(ipAddress).moduleId(-1L)
                .userId(userId).status(status).build();
        secAccessLogRepository.save(secAccessLog);
    }

    private long checkPasswordExpireDuration(SecUser secUser) {
        if ("2".equals(secUser.getPasswordExpireStatus())) {
            return -1;
        }

        SecSystemPolicy secSystemPolicy = secSystemPolicyRepository.findByPolicyType(PASSWORD_EXPIRE_DURATION);
        String passwordExpireDurationStr = secSystemPolicy.getPolicyValue();
        int passwordExpireDuration = passwordExpireDurationStr == null ? 0 : Integer.parseInt(passwordExpireDurationStr);

        Date currentDate = new Date();
        long remainPasswordExpire = -1;

        //do not check password expire
        if (passwordExpireDuration == 0)
            return remainPasswordExpire;

        if ("1".equals(secUser.getPasswordExpireStatus())) {
            if (secUser.getLastChangePassword() == null) {
                secUser.setPasswordExpireStatus("0");
                secUserRepository.save(secUser);
                throw new AppException("ERROR_001", ErrorCode.ERROR_001);
            } else {
                if (currentDate.compareTo(DateUtils.addDays(secUser.getLastChangePassword(), passwordExpireDuration)) >= 0) {
                    secUser.setPasswordExpireStatus("0");
                    secUserRepository.save(secUser);
                    throw new AppException("ERROR_001", ErrorCode.ERROR_001);
                } else {
                    LocalDate now = LocalDate.now();
                    LocalDate lastChangePassword = toLocalDate(DateUtils.addDays(secUser.getLastChangePassword(), passwordExpireDuration));
                    remainPasswordExpire = ChronoUnit.DAYS.between(now, lastChangePassword);
                }
            }
        } else {
            //password is expired
            throw new AppException("ERROR_001", ErrorCode.ERROR_001);
        }
        return remainPasswordExpire;
    }

    private boolean checkSchedulePermission(List<ScheduleData> scheduleDataList) {
        if (scheduleDataList == null || scheduleDataList.isEmpty())
            return false;
        //throw new AppException("ERROR_005", ErrorCode.ERROR_005);

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(currentDate);
        int dayNum = currentDate.getDay() + 1;
        for (ScheduleData s : scheduleDataList) {
            if (s.getDayId() != dayNum) {
                continue;
            }
            if (DateUtil.isInBetween(s.getStartTime(), s.getEndTime(), currentTime)) {
                return true;
            }
        }

        return false;
        //throw new AppException("ERROR_007", ErrorCode.ERROR_007);
    }


    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
