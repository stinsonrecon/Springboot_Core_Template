package vn.com.hust.identityservice.repository;


import vn.com.hust.admin.model.SecScheduleDetail;

import java.util.List;


public interface SecScheduleDetailRepository {

    public List<SecScheduleDetail> findByScheduleId(Long scheduleId);
}
