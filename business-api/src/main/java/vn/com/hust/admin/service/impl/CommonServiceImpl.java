package vn.com.hust.admin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.hust.admin.model.SecDepartment;
import vn.com.hust.admin.repository.SecDepartmentRepository;
import vn.com.hust.admin.service.CommonService;

/**
 * @author TienLM20
 * @since 09/07/2023
 */

@Slf4j
@Service
@Transactional
public class CommonServiceImpl implements CommonService {
    @Autowired
    SecDepartmentRepository secDepartmentRepository;

    @Override
    public SecDepartment getDepartmentInfoByUserName(String userName) {
        return secDepartmentRepository.getDepartmentInfoByUserName(userName);
    }
}
