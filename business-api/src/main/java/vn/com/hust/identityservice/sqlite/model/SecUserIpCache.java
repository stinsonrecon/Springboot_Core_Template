package vn.com.hust.identityservice.sqlite.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.com.hust.identityservice.model.UserIp;

@Getter
@Setter
@Builder
public class SecUserIpCache {
    private Long userId;
    private String userName;
    private Long ipStart;
    private Long ipEnd;

    public static SecUserIpCache from(UserIp userIp) {
        return SecUserIpCache.builder()
                .userId(userIp.getUserId())
                .userName(userIp.getUserName())
                .ipStart(userIp.getIpStart())
                .ipEnd(userIp.getIpEnd())
                .build();
    }
}
