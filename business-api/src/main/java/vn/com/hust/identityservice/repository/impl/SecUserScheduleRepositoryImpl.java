package vn.com.hust.identityservice.repository.impl;

import org.springframework.stereotype.Component;
import vn.com.hust.identityservice.repository.SecUserScheduleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SecUserScheduleRepositoryImpl implements SecUserScheduleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Integer> findScheduleByUserId(Long userId) {
        Set<Integer> schedules = new HashSet<>();
        String sql = "select ss.schedule_id from SEC_USER_SCHEDULE sus, SEC_SCHEDULE ss where user_id = ? and ss.schedule_id = sus.schedule_id and ss.status = 1 ";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        List<Integer> listResult = query.getResultList();
        for (Integer scheduleId : listResult)
            schedules.add(scheduleId);
        return schedules;
    }

}
