package vn.com.hust.admin.service;

import vn.com.hust.admin.model.SecDepartment;

/**
 * @author TienLM20
 * @since 09/07/2023
 */

public interface CommonService {
    SecDepartment getDepartmentInfoByUserName(String userName);
}
