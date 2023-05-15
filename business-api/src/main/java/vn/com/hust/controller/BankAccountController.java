package vn.com.hust.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.core.common.config.EnableWrapResponse;
import vn.com.core.common.generics.controller.GenericController;
import vn.com.hust.common.AppPath;
import vn.com.hust.dto.BankAccountDTO;
import vn.com.hust.entity.BankAccount;
import vn.com.hust.service.BankAccountService;

@Slf4j
@RestController
@RequestMapping("/bank-account")
@EnableWrapResponse
public class BankAccountController extends GenericController<BankAccount, Long> {
    @Autowired
    BankAccountService service;

    @PostMapping(AppPath.REQUEST_MAPPING.GET_ALL_BANK_ACCOUNT_BY_DEPARTMENT)
    public ResponseEntity getAllByDepartment(@RequestBody BankAccount request){
        return ResponseEntity.ok(service.findAllByDepartment(request));
    }

    @PostMapping(AppPath.REQUEST_MAPPING.ON_SEARCH_BANK_ACCOUNT)
    public ResponseEntity onSearch(@RequestBody BankAccountDTO request){
        return ResponseEntity.ok(service.onSearchBankAccount(request));
    }
}
