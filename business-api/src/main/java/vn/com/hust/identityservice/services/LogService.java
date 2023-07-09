package vn.com.hust.identityservice.services;


import vn.com.hust.admin.model.SecAccessLog;
import vn.com.hust.identityservice.message.request.AccessLogRequest;
import vn.com.hust.identityservice.message.request.LogRequest;
import vn.com.hust.identityservice.message.response.LogResponse;

public interface LogService {
    SecAccessLog logWhenAccessModule(AccessLogRequest accessLogRequest, String userId, String ipAddress);

    SecAccessLog logWhenAccessModuleAndPath(String path, String appCode, String userId, String ipAddress,String agent);

    LogResponse searchLog(LogRequest request);

    LogResponse exportLog(LogRequest request);
}
