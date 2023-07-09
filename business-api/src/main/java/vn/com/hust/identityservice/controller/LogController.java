package vn.com.hust.identityservice.controller;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.core.common.security.JwtProvider;
import vn.com.core.common.utils.WebUtils;
import vn.com.hust.identityservice.message.request.AccessLogRequest;
import vn.com.hust.identityservice.message.request.AccessPathRequest;
import vn.com.hust.identityservice.message.request.LogRequest;
import vn.com.hust.identityservice.message.response.AccessLogResponse;
import vn.com.hust.identityservice.services.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    @Qualifier("corpLogService")
    LogService logService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/access")
    public ResponseEntity<AccessLogResponse> logWhenAccessModule(@RequestHeader(name = "Authorization") String token, HttpServletRequest request, @Valid @RequestBody AccessLogRequest accessLogRequest) {
        AccessLogResponse accessLogResponse = AccessLogResponse.from(logService.logWhenAccessModule(accessLogRequest, jwtProvider.getUserIdFromTokenRSA(token), WebUtils.getClientIp(request)));
        return new ResponseEntity<>(accessLogResponse, HttpStatus.OK);
    }

    @PostMapping("/access-path")
    public ResponseEntity<AccessLogResponse> logWhenAccessModuleAndPath(@RequestHeader(name = "Authorization") String token, HttpServletRequest request, @Valid @RequestBody AccessPathRequest accessPathRequest) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String agent = userAgent.getBrowser().getName().toString();
        AccessLogResponse accessLogResponse = AccessLogResponse.from(logService.logWhenAccessModuleAndPath(accessPathRequest.getPath(), accessPathRequest.getAppCode(), jwtProvider.getUserIdFromTokenRSA(token), WebUtils.getClientIp(request),agent));
        return new ResponseEntity<>(accessLogResponse, HttpStatus.OK);
    }

    @RequestMapping(value = {"/search-log"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchLog(@RequestBody LogRequest request) {
        return ResponseEntity.ok(logService.searchLog(request));
    }

    @RequestMapping(value = {"/export-log"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> exportLog(@RequestBody LogRequest request) {
        return ResponseEntity.ok(logService.exportLog(request));
    }

}
