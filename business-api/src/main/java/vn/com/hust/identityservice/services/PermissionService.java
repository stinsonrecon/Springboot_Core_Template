package vn.com.hust.identityservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.hust.admin.model.CurrentUserData;
import vn.com.hust.identityservice.model.ScheduleDetail;
import vn.com.hust.identityservice.repository.PermissionRepositoryExt;

import java.util.List;
import java.util.Set;

@Service
public class PermissionService {

    @Autowired
    PermissionRepositoryExt permissionRepositoryExt;

    boolean isAccessByUser(Long userId, String path) {
        return permissionRepositoryExt.isAccessByUser(userId, path);
    }

    public Set<Long> getScheduleByUserId(Long userId) {
        return permissionRepositoryExt.getScheduleByUserId(userId);
    }

    public List<ScheduleDetail> getListScheduleDetailById(Long scheduleId) {
        return permissionRepositoryExt.getListScheduleDetailById(scheduleId);
    }


    public List<Long> getGrouphierarchyByUserId(Long userId) {
        return permissionRepositoryExt.getGrouphierarchyByUserId(userId);
    }


    public CurrentUserData getCurrentUserData(String userName, Long deptId) {
        return permissionRepositoryExt.getCurrentUserData(userName, deptId);
    }
}
