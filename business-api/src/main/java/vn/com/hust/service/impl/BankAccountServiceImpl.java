package vn.com.hust.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import vn.com.core.common.generics.impl.GenericServiceImpl;
import vn.com.hust.dao.BankAccountDao;
import vn.com.hust.dto.BankAccountDTO;
import vn.com.hust.entity.BankAccount;
import vn.com.hust.repository.BankAccountRepository;
import vn.com.hust.service.BankAccountService;

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
