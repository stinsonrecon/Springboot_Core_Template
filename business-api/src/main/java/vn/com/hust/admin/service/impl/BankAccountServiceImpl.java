package vn.com.hust.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.core.common.generics.impl.GenericServiceImpl;
import vn.com.hust.admin.dto.BankAccountDTO;
import vn.com.hust.admin.entity.BankAccount;
import vn.com.hust.admin.repository.BankAccountRepository;
import vn.com.hust.admin.service.BankAccountService;

@Service
public class BankAccountServiceImpl extends GenericServiceImpl<BankAccount, Long> implements BankAccountService {
    @Autowired
    private BankAccountRepository repo;

    @Override
    public Object findAllByDepartment(BankAccount request) {
        return repo.findBankAccountsByDepartment(request.getDepartment());
    }

    @Override
    public Object onSearchBankAccount(BankAccountDTO request) {
        return repo.onSearch(request);
    }
}
