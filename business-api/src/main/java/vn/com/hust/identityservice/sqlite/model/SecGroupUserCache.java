package vn.com.hust.identityservice.sqlite.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.com.hust.identityservice.model.GroupUser;

@Getter
@Setter
@Builder
public class SecGroupUserCache {
    private Long userId;
    private Long groupId;

    public static SecGroupUserCache from(GroupUser groupUser) {
        return SecGroupUserCache.builder()
                .groupId(groupUser.getGroupId())
                .userId(groupUser.getUserId())
                .build();
    }

}
