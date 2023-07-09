package vn.com.hust.identityservice.sqlite.service;


import vn.com.hust.identityservice.sqlite.model.SecGroupModuleCache;
import vn.com.hust.identityservice.sqlite.model.SecGroupScheduleCache;
import vn.com.hust.identityservice.sqlite.model.SecUserCache;
import vn.com.hust.identityservice.sqlite.model.SecUserIpCache;

import java.util.List;

public interface SqliteService {
    public List<SecUserCache> findAllUser();

    public List<SecGroupScheduleCache> findGroupSchedule();

    public List<SecUserIpCache> findUserIp();

    public List<SecGroupModuleCache> findGroupModule();
}
