package vn.com.hust.identityservice.sqlite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import vn.com.hust.identityservice.model.*;
import vn.com.hust.identityservice.sqlite.model.*;
import vn.com.hust.identityservice.sqlite.repository.*;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SqliteCacheService {
    @Autowired
    @Qualifier("SQLiteSecUserRepository")
    SecUserRepository secUserRepo;

    @Autowired
    SecUserIpRepository secUserIpRepo;

    @Autowired
    SecGroupScheduleRepository secGroupScheduleRepo;

    @Autowired
    @Qualifier("SQLiteSecGroupModuleRepository")
    SecGroupModuleRepository secGrouModuleRepo;

    @Autowired
    @Qualifier("SQLiteSecGroupUserRepository")
    SecGroupUserRepository secGroupUserRepo;

    @Autowired
    @Qualifier("SQLiteSecGroupRepository")
    SecGroupRepository secGroupRepo;

    @Autowired
            @Qualifier("SQLiteSecCommonRepository")
    SecCommonRepository secCommonRepo;

    @Autowired
    @Qualifier("SQLiteSecGroupPositionRepository")
    SecGroupPositionRepository secGroupPositionRepository;

    public void cacheUser(List<User> users, List<UserIp> userIps, List<GroupSchedule> groupSchedules, List<GroupUser> groupUser, Connection conn, List<GroupPosition> groupPositions) {
        secUserRepo.delete(conn);
        secUserIpRepo.delete(conn);
        secGroupScheduleRepo.delete(conn);
        secGroupUserRepo.delete(conn);
        //B1_EVN
        secGroupPositionRepository.delete(conn);
        //B1_EVN

        secUserRepo.saveAll(users.stream().map(x -> SecUserCache.from(x)).collect(Collectors.toList()), conn);
        secUserIpRepo.saveAll(userIps.stream().map(x -> SecUserIpCache.from(x)).collect(Collectors.toList()), conn);
        secGroupScheduleRepo.saveAll(groupSchedules.stream().map(x -> SecGroupScheduleCache.from(x)).collect(Collectors.toList()), conn);
        secGroupUserRepo.saveAll(groupUser.stream().map(x -> SecGroupUserCache.from(x)).collect((Collectors.toList())), conn);
        //B1_EVN
        secGroupPositionRepository.saveAll(groupPositions.stream().map(x -> SecGroupPositionCache.from(x)).collect((Collectors.toList())), conn);
        //B1_EVN
    }

    public void cacheRole(List<GroupModule> groupModules, List<Group> groups, Connection conn) {
        secGrouModuleRepo.delete(conn);
        secGroupRepo.delete(conn);

        secGrouModuleRepo.saveAll(groupModules.stream().map(x -> SecGroupModuleCache.from(x)).collect(Collectors.toList()), conn);
        secGroupRepo.saveAll(groups.stream().map(x -> SecGroupCache.from(x)).collect(Collectors.toList()), conn);
    }


    public List<SecGroupScheduleCache> getListGroupScheduleCacheFromGroupId(Long groupId) {
        return secGroupScheduleRepo.findAllByGroupId(groupId);
    }

    public List<Long> getGroupsFromUserId(Long userId) {
        return secGroupUserRepo.findGroupsByUserId(userId);
    }

    public List<SecUserIpCache> getListUserIpCacheFromUserName(String username) {
        return secUserIpRepo.findAllByUserName(username);
    }

    public SecUserCache getUserCacheFromUsername(String username) {
        return secUserRepo.findByUserName(username).isEmpty() ? null : secUserRepo.findByUserName(username).get(0);
    }

    public boolean isAccessToPathByGroup(String path, List<Long> groups) throws Exception {
        return secCommonRepo.isAccessToPathByGroup(path, groups);
    }

}
