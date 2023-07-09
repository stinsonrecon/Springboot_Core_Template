package vn.com.hust.identityservice.message.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank
    private String username;

//    @NotBlank
    private String password;

    @NotBlank
    private String ipAddress;

    @NotBlank
    private String appCode;

}
