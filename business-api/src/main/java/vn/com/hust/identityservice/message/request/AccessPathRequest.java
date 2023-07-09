package vn.com.hust.identityservice.message.request;

import lombok.Data;

@Data
public class AccessPathRequest {
    private String appCode;
    private String path;
}
