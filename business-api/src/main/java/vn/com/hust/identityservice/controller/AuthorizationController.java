package vn.com.hust.identityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.hust.identityservice.message.request.AccessPermissionRequest;
import vn.com.hust.identityservice.message.response.AccessPermissionResponse;
import vn.com.hust.identityservice.services.AuthorizationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/authz")
public class AuthorizationController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/is-access")
    public ResponseEntity<AccessPermissionResponse> isAccessPermission(@RequestBody @Valid AccessPermissionRequest accessPermissionRequest) throws Exception {
        AccessPermissionResponse accessPermissionResponse = AccessPermissionResponse.builder().result(authorizationService.isAccessPermission(accessPermissionRequest.getPath(), Long.valueOf(accessPermissionRequest.getUserId()))).build();
        return ResponseEntity.ok(accessPermissionResponse);
    }

    @PostMapping("/is-valid-user")
    public ResponseEntity<AccessPermissionResponse> isValidUser(@RequestBody @Valid AccessPermissionRequest accessPermissionRequest) {
        AccessPermissionResponse accessPermissionResponse = AccessPermissionResponse.builder().result(authorizationService.isValidUserInfo(accessPermissionRequest.getUsername())).build();
        return ResponseEntity.ok(accessPermissionResponse);
    }

    @PostMapping("/is-valid-schedule")
    public ResponseEntity<AccessPermissionResponse> isValidSchedule(@RequestBody @Valid AccessPermissionRequest accessPermissionRequest) {
        AccessPermissionResponse accessPermissionResponse = AccessPermissionResponse.builder().result(authorizationService.isValidSchedulePermission(Long.valueOf(accessPermissionRequest.getUserId()))).build();
        return ResponseEntity.ok(accessPermissionResponse);
    }

}
