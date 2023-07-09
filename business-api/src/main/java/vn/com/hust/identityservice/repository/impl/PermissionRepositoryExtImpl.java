package vn.com.hust.identityservice.repository.impl;

import org.springframework.stereotype.Repository;
import vn.com.hust.admin.model.CurrentUserData;
import vn.com.hust.identityservice.model.ScheduleDetail;
import vn.com.hust.identityservice.repository.PermissionRepositoryExt;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class PermissionRepositoryExtImpl implements PermissionRepositoryExt {
    public static final String TYPE_AP_DOMAIN_DAY_OF_WEEK = "DAY_OF_WEEK";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isAccessByUser(Long userId, String path) {
        String sqlModule = "select module_id from SEC_MODULE where path = ? ";
        Query queryModule = entityManager.createNativeQuery(sqlModule);
        queryModule.setParameter(1, path);
        List<BigDecimal> moduleIds = queryModule.getResultList();
        if (moduleIds.isEmpty())
            return false;


        String sqUserlModule = "select count(1) from SEC_USER_MODULE where user_id = ? and module_id = ?";
        for (BigDecimal moduleId : moduleIds) {
            Query queryModuleUser = entityManager.createNativeQuery(sqUserlModule);
            queryModuleUser.setParameter(1, userId);
            queryModuleUser.setParameter(2, moduleId.longValue());
            Long count = ((BigDecimal) queryModuleUser.getSingleResult()).longValue();
            if (count - 0 > 0)
                return true;
        }
        return false;
    }

    @Override
    public Set<Long> getScheduleByUserId(Long userId) {
        Set<Long> schedules = new HashSet<>();
        String sql = "select distinct ss.schedule_id from SEC_USER_SCHEDULE sus, SEC_SCHEDULE ss where user_id = ? and sus.schedule_id = ss.schedule_id and ss.status = 1 ";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        List<BigDecimal> listResult = query.getResultList();
        for (BigDecimal schedule : listResult)
            schedules.add(schedule.longValue());
        return schedules;
    }

    @Override
    public List<ScheduleDetail> getListScheduleDetailById(Long scheduleId) {
        String sql = " select SCHEDULE_ID, DATE_FORMAT(SYSDATE(), '%Y/%m/%d') || START_TIME START_TIME, DATE_FORMAT(SYSDATE(), '%Y/%m/%d') || END_TIME END_TIME, day_id " +
                " from SEC_SCHEDULE_DETAIL where SCHEDULE_ID = ? and day_id = (select code from AP_DOMAIN where type = '" + TYPE_AP_DOMAIN_DAY_OF_WEEK + "' and status = 1 " +
                " and name = replace((SELECT DAYNAME(SYSDATE()) day FROM dual), ' ', '')) ";
        Query query = entityManager.createNativeQuery(sql, ScheduleDetail.class);
        query.setParameter(1, scheduleId);
        List<ScheduleDetail> scheduleDetails = query.getResultList();

        if (scheduleDetails == null || scheduleDetails.isEmpty())
            return new ArrayList<>();

        return scheduleDetails;
    }

    @Override
    public List<Long> getGrouphierarchyByUserId(Long userId) {
        List<Long> groups = new ArrayList<>();
        String sqlGroup = "SELECT     sg.GROUP_ID" +
                "     FROM SEC_GROUP sg " +
                "    WHERE NVL (status, 0) > 0 " +
                "START WITH sg.GROUP_ID IN (SELECT sgu.GROUP_ID " +
                "                            FROM SEC_GROUP_USER sgu " +
                "                           WHERE sgu.user_id = ? " +
                "							UNION ALL " +
                "							SELECT sgp.GROUP_ID FROM SEC_GROUP_POSITION sgp where sgp.position_id = " +
                "							(SELECT position_id FROM V_SEC_USER where user_id = ? )" +
                " 							) " +
                "CONNECT BY PRIOR sg.parent_group_id = sg.GROUP_ID ";
        Query queryGroup = entityManager.createNativeQuery(sqlGroup);
        queryGroup.setParameter(1, userId);
        queryGroup.setParameter(2, userId);
        List<BigDecimal> result = queryGroup.getResultList();
        for (BigDecimal a : result)
            groups.add(a.longValue());
        return groups;
    }

    @Override
    public CurrentUserData getCurrentUserData(String userName, Long deptId) {
        String sb = "SELECT " +
                "    u.user_id, " +
                "    u.user_name, " +
                "    u.full_name, " +
                "    u.position_id," +
                "    dep.dept_id, " +
                "    dep.org_id, " +
                "    dep.name, " +
                "    dep.short_name " +
                "FROM " +
                "    V_SEC_USER      u, " +
                "    SEC_DEPARTMENT  dep " +
                "WHERE " +
                "        u.user_name = upper(:user_name) " +
                "    AND dep.dept_Id = :deptId";

        Query query = entityManager.createNativeQuery(sb, CurrentUserData.class);
        query.setParameter("user_name", userName);
        query.setParameter("deptId", deptId);
        List<CurrentUserData> lstData = query.getResultList();
        if (lstData != null && lstData.size() > 0)
            return lstData.get(0);

        return null;
    }
}
