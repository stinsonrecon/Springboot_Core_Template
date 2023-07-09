package vn.com.hust.identityservice.sqlite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import vn.com.hust.identityservice.sqlite.model.SecGroupModuleCache;
import vn.com.hust.identityservice.sqlite.model.SecGroupScheduleCache;
import vn.com.hust.identityservice.sqlite.model.SecUserCache;
import vn.com.hust.identityservice.sqlite.model.SecUserIpCache;
import vn.com.hust.identityservice.sqlite.repository.SecGroupModuleRepository;
import vn.com.hust.identityservice.sqlite.repository.SecGroupScheduleRepository;
import vn.com.hust.identityservice.sqlite.repository.SecUserIpRepository;
import vn.com.hust.identityservice.sqlite.repository.SecUserRepository;

import java.util.List;

@Service
public class SqliteServiceImpl implements SqliteService {
    @Autowired
    @Qualifier("SQLiteSecUserRepository")
    SecUserRepository secUserRepo;

    @Autowired
    @Qualifier("SQLiteSecGroupScheduleRepository")
    SecGroupScheduleRepository secGroupScheduleRepo;

    @Autowired
    @Qualifier("SQLiteSecUserIpRepository")
    SecUserIpRepository secUserIpRepo;

    @Autowired
    @Qualifier("SQLiteSecGroupModuleRepository")
    SecGroupModuleRepository secGroupModuleRepo;

    @Override
    public List<SecUserCache> findAllUser() {
        return secUserRepo.findAll();
    }

    @Override
    public List<SecGroupScheduleCache> findGroupSchedule() {
        return secGroupScheduleRepo.findAll();
    }

    @Override
    public List<SecUserIpCache> findUserIp() {
        return secUserIpRepo.findAll();
    }

    @Override
    public List<SecGroupModuleCache> findGroupModule() {
        return secGroupModuleRepo.findAll();
    }

}
