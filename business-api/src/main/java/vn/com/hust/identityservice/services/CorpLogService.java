package vn.com.hust.identityservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.hust.admin.model.SecAccessLog;
import vn.com.hust.admin.model.SecModule;
import vn.com.hust.admin.repository.SecAccessLogRepository;
import vn.com.hust.identityservice.message.request.AccessLogRequest;
import vn.com.hust.identityservice.message.request.LogRequest;
import vn.com.hust.identityservice.message.response.LogResponse;
import vn.com.hust.identityservice.repository.LogRepository;
import vn.com.hust.identityservice.repository.SecModuleExRepository;

import java.util.Date;
import java.util.List;

@Service
public class CorpLogService implements LogService {

    @Autowired
    SecAccessLogRepository secAccessLogRepository;

    @Autowired
    SecModuleExRepository secModuleExRepository;

    @Autowired
    LogRepository logRepository;

    public SecAccessLog logWhenAccessModule(AccessLogRequest accessLogRequest, String userId, String ipAddress) {
        SecAccessLog secAccessLog = accessLogRequest.toSecAccessLog(userId, ipAddress);
        return secAccessLogRepository.save(secAccessLog);
    }

    public SecAccessLog logWhenAccessModuleAndPath(String path, String appCode, String userId, String ipAddress,String agent) {
        SecModule module = secModuleExRepository.getModuleIdByPathAndAppCode(path, appCode);
        SecAccessLog secAccessLog = SecAccessLog.builder()
                .userId(userId).ipAddress(ipAddress).accessDate(new Date()).moduleId(module.getModuleId()).appId((long) module.getSecApp().getAppId()).agent(agent).build();
        return secAccessLogRepository.save(secAccessLog);
    }

    @Override
    public LogResponse searchLog(LogRequest request) {
        List<LogResponse> objects = logRepository.searchLog(request);

        LogResponse response = new LogResponse();

        Long total = logRepository.countLog(request);

        response.setTotal(total);
        response.setItems(objects);
        return response;
    }

    @Override
    public LogResponse exportLog(LogRequest request) {
        return logRepository.exportLog(request);
    }
}
