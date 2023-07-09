package vn.com.hust.identityservice.repository;


import vn.com.hust.admin.model.CurrentUserData;
import vn.com.hust.identityservice.model.ScheduleDetail;

import java.util.List;
import java.util.Set;

public interface PermissionRepositoryExt {

    boolean isAccessByUser(Long userId, String path);

    Set<Long> getScheduleByUserId(Long userId);

    List<ScheduleDetail> getListScheduleDetailById(Long scheduleId);

    List<Long> getGrouphierarchyByUserId(Long userId);

    CurrentUserData getCurrentUserData(String userName, Long deptId);
}
