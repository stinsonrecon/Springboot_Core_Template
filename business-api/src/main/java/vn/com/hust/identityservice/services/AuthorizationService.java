package vn.com.hust.identityservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.hust.identityservice.model.ScheduleDetail;
import vn.com.hust.identityservice.sqlite.model.SecGroupScheduleCache;
import vn.com.hust.identityservice.sqlite.model.SecUserCache;
import vn.com.hust.identityservice.sqlite.service.SqliteCacheService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class AuthorizationService {

    public static final String MAX_LOGIN_FAILURE = "MAX_LOGIN_FAILURE";

    @Autowired
    SqliteCacheService sqliteCacheService;

    @Autowired
    PermissionService permissionService;

    public boolean isAccessPermission(String path, Long userId) throws Exception {
        List<Long> groups = permissionService.getGrouphierarchyByUserId(userId);
        boolean isAccessByGroup = sqliteCacheService.isAccessToPathByGroup(path, groups);
        if (isAccessByGroup)
            return true;
        boolean isAccessByUser = permissionService.isAccessByUser(userId, path);
        return isAccessByUser;
    }

    public boolean isValidUserInfo(String username) {
        SecUserCache userCache = sqliteCacheService.getUserCacheFromUsername(username);
        if (userCache == null)
            return false;
        if (userCache.getPasswordExpireStatus() != null && !userCache.getPasswordExpireStatus().equals("0")) {
            return true;
        }
        return false;
    }


    public boolean isValidSchedulePermission(Long userId) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateStr = sdf.format(date);
        //check by group
        List<Long> groups = sqliteCacheService.getGroupsFromUserId(userId);
        List<SecGroupScheduleCache> groupScheduleCaches = new ArrayList<>();
        for (Long s : groups) {
            groupScheduleCaches.addAll(sqliteCacheService.getListGroupScheduleCacheFromGroupId(s));
        }
        for (SecGroupScheduleCache g : groupScheduleCaches) {
            if (isBetweenStringRange(g.getAccessTimeStart(), g.getAccessTimeEnd(), dateStr)) {
                return true;
            }
        }

        //check by user
        Set<Long> listSchedule = permissionService.getScheduleByUserId(userId);
        List<ScheduleDetail> scheduleDetails = new ArrayList<>();
        for (Long schedule : listSchedule) {
            scheduleDetails.addAll(permissionService.getListScheduleDetailById(schedule));
        }
        for (ScheduleDetail g : scheduleDetails) {
            if (isBetweenStringRange(g.getAccessTimeStart(), g.getAccessTimeEnd(), dateStr)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBetweenStringRange(String startRange, String endRange, String checkRange) {
        if (checkRange.compareTo(startRange) >= 0 && checkRange.compareTo(endRange) <= 0) {
            return true;
        }
        return false;
    }

    private boolean isBetweenNumberRange(Long startRange, Long endRange, Long checkRange) {
        if ((startRange.intValue() == 0) && (endRange.intValue() == 0))
            return true;
        if ((checkRange.compareTo(startRange) >= 0) && (checkRange.compareTo(endRange) <= 0)) {
            return true;
        }
        return false;
    }
}
