package vn.com.hust.identityservice.repository;

import java.util.Set;


public interface SecUserScheduleRepository {

    public Set<Integer> findScheduleByUserId(Long userId);
}
