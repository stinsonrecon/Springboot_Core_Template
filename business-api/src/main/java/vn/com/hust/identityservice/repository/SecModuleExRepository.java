package vn.com.hust.identityservice.repository;


import vn.com.hust.admin.model.SecModule;

public interface SecModuleExRepository {
    SecModule getModuleIdByPathAndAppCode(String path, String appCode);
}
