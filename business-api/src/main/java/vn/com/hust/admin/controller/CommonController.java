package vn.com.hust.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.core.common.config.EnableWrapResponse;
import vn.com.hust.admin.common.AppPath;
import vn.com.hust.admin.entity.BankAccount;
import vn.com.hust.admin.service.CommonService;

/**
 * @author TienLM20
 * @since 09/07/2023
 */

@Slf4j
@RestController
@RequestMapping("/common")
@EnableWrapResponse
public class CommonController {
    @Autowired
    CommonService commonService;

    @GetMapping(AppPath.REQUEST_MAPPING.GET_USER_INFO)
    public ResponseEntity getUserInfo(@RequestParam("userName") String userName){
        return ResponseEntity.ok(commonService.getDepartmentInfoByUserName(userName));
    }
}
