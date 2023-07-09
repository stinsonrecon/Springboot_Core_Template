package vn.com.hust.identityservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.hust.identityservice.sqlite.model.SecGroupModuleCache;
import vn.com.hust.identityservice.sqlite.model.SecGroupScheduleCache;
import vn.com.hust.identityservice.sqlite.model.SecUserCache;
import vn.com.hust.identityservice.sqlite.model.SecUserIpCache;
import vn.com.hust.identityservice.sqlite.service.SqliteService;

import java.util.List;

@RestController
public class SqliteMobitorController {
    @Autowired
    SqliteService service;

    @GetMapping("/users")
    public ResponseEntity<List<SecUserCache>> findAllUser() {
        List<SecUserCache> lstUserCache = service.findAllUser();
        return ResponseEntity.ok(lstUserCache);
    }

    @GetMapping("/group-schedule")
    public ResponseEntity<List<SecGroupScheduleCache>> findGroupSchedule() {
        List<SecGroupScheduleCache> lstGroupScheduleCache = service.findGroupSchedule();
        return ResponseEntity.ok(lstGroupScheduleCache);
    }

    @GetMapping("/user-ip")
    public ResponseEntity<List<SecUserIpCache>> findUserIp() {
        List<SecUserIpCache> lstUserIp = service.findUserIp();
        return ResponseEntity.ok(lstUserIp);
    }

    @GetMapping("/group-module")
    public ResponseEntity<List<SecGroupModuleCache>> findgroupModule() {
        List<SecGroupModuleCache> lstGroupModule = service.findGroupModule();
        return ResponseEntity.ok(lstGroupModule);
    }
}
