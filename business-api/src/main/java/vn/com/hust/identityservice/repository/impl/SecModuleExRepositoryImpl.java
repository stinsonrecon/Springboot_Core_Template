package vn.com.hust.identityservice.repository.impl;

import org.springframework.stereotype.Component;
import vn.com.core.common.exceptions.AppException;
import vn.com.hust.admin.model.SecModule;
import vn.com.hust.identityservice.repository.SecModuleExRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class SecModuleExRepositoryImpl implements SecModuleExRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SecModule getModuleIdByPathAndAppCode(String path, String appCode) {
        String sql = " select * from SEC_MODULE where path = :PATH and app_id = (select app_id from SEC_APP where app_code = :APP_CODE) and module_type = '2' ";
        Query query = entityManager.createNativeQuery(sql, SecModule.class);
        query.setParameter("PATH", path);
        query.setParameter("APP_CODE", appCode);

        List<SecModule> listResult = query.getResultList();

        if (listResult == null || listResult.isEmpty())
            throw new AppException("URI Path not found");

        return listResult.get(0);
    }
}
