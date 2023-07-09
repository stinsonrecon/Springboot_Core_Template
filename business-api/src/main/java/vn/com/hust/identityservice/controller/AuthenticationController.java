package vn.com.hust.identityservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.core.common.exceptions.AppException;
import vn.com.hust.admin.common.Constants;
import vn.com.hust.admin.data.CommonData;
import vn.com.hust.identityservice.config.Base64ShaPasswordEncoder;
import vn.com.hust.identityservice.data.ErrorCode;
import vn.com.hust.identityservice.data.TokenData;
import vn.com.hust.identityservice.message.request.LoginRequest;
import vn.com.hust.identityservice.message.request.RefreshRequest;
import vn.com.hust.identityservice.message.response.RefreshResponse;
import vn.com.hust.identityservice.services.AuthenticationService;
import vn.com.hust.identityservice.services.JwtService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Environment env;

    @Autowired
    @Qualifier("corpAuthenticationService")
    AuthenticationService authenticationService;

    @Autowired
    JwtService jwtService;

    @Autowired
    public AuthenticationController(Environment env) {
        this.env = env;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@RequestHeader(name = "Authorization") String accessToken, @Valid @RequestBody RefreshRequest refreshRequest) {
        TokenData tokenData = authenticationService.refresh(refreshRequest.getRefreshToken(), accessToken);
        return ResponseEntity.ok(RefreshResponse.builder()
                .accessToken(tokenData.getAccessToken())
                .refreshToken(tokenData.getRefreshToken())
                .accessTokenExpirationSecond(tokenData.getAccessTokenExpirationSecond())
                .refreshTokenExpirationSecond(tokenData.getRefreshTokenExpirationSecond())
                .build());
    }

    //hanx chuyen shop -> org
    @PostMapping("/refresh-change-org")
    public ResponseEntity<RefreshResponse> refreshChangeOrg(@RequestHeader(name = "Authorization") String accessToken, @Valid @RequestBody RefreshRequest refreshRequest) {
        TokenData tokenData = authenticationService.refreshChangeOrg(refreshRequest.getRefreshToken(), accessToken, refreshRequest.getDeptId());
        return ResponseEntity.ok(RefreshResponse.builder()
                .accessToken(tokenData.getAccessToken())
                .refreshToken(tokenData.getRefreshToken())
                .accessTokenExpirationSecond(tokenData.getAccessTokenExpirationSecond())
                .refreshTokenExpirationSecond(tokenData.getRefreshTokenExpirationSecond())
                .build());
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader(name = "Authorization") String token) {
        authenticationService.logout(token);
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo(@Valid @RequestParam Long userId) {
        return ResponseEntity.ok(authenticationService.getUserInfoAdmin(userId));
    }

    @PostMapping("/generateTokenExpiration100Year")
    public ResponseEntity<CommonData> getTokenTimeForever(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.generateTokenExpiration100Year(loginRequest));
    }

    @PostMapping("/getToken")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest requestDTO, HttpServletRequest servletRequest) {
        Base64ShaPasswordEncoder base64SHAPasswordEncoder = new Base64ShaPasswordEncoder();
        String username = env.getProperty("admin-username", "");
        String password = env.getProperty("admin-password", "");
        String passEncode = base64SHAPasswordEncoder.encode((requestDTO.getPassword()));
        if (!requestDTO.getUsername().equals(username) || !passEncode.equals(password)) {
            throw new AppException(Constants.ERROR_SYSTEM, ErrorCode.ERROR_009);
        }
        return new ResponseEntity<>(jwtService.getTokenAPI(username), HttpStatus.OK);
    }

    @GetMapping("/getTokenByUserName")
    public ResponseEntity<CommonData> getTokenByUserName(@Valid @RequestParam String userName) {
        return ResponseEntity.ok(authenticationService.getTokenByUserName(userName));
    }
}
