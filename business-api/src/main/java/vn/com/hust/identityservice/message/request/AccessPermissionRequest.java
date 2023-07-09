package vn.com.hust.identityservice.message.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class AccessPermissionRequest {

    private Set<String> listGroup;
    private String path;
    private String method;
    private String username;
    private String ipAddress;
    private String userId;
}
