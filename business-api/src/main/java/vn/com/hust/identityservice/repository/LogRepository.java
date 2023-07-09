package vn.com.hust.identityservice.repository;


import vn.com.hust.identityservice.message.request.LogRequest;
import vn.com.hust.identityservice.message.response.LogResponse;

import java.util.List;

public interface LogRepository {
    List<LogResponse> searchLog(LogRequest request) ;

    Long countLog(LogRequest request);

    LogResponse exportLog(LogRequest request);
}
