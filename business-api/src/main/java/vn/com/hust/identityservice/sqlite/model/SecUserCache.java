package vn.com.hust.identityservice.sqlite.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.com.hust.identityservice.model.User;

@Getter
@Setter
@Builder
public class SecUserCache {
    private Long userId;
    private String userName;
    private String passwordExpireStatus;
    private int status;
    private int loginFailureCount;
    private Long positionId;

    public static SecUserCache from(User user) {
        return SecUserCache.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .passwordExpireStatus(user.getPasswordExpireStatus())
                .loginFailureCount(user.getLoginFailureCount())
                .status(user.getStatus())
                .positionId(user.getPositionId())
                .build();
    }
}
