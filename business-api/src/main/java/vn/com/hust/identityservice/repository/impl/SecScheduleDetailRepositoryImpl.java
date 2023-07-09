package vn.com.hust.identityservice.repository.impl;

import org.springframework.stereotype.Component;
import vn.com.hust.admin.model.SecScheduleDetail;
import vn.com.hust.identityservice.repository.SecScheduleDetailRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class SecScheduleDetailRepositoryImpl implements SecScheduleDetailRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<SecScheduleDetail> findByScheduleId(Long scheduleId) {
        String sql = "select SCHEDULE_ID, DAY_ID, START_TIME, END_TIME from SEC_SCHEDULE_DETAIL where SCHEDULE_ID = ?";
        Query query = entityManager.createNativeQuery(sql, SecScheduleDetail.class);
        query.setParameter(1, scheduleId);
        List<SecScheduleDetail> result = query.getResultList();
        return result;
    }

}
