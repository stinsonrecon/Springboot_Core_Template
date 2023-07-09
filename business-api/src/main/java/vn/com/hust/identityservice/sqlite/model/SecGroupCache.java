package vn.com.hust.identityservice.sqlite.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.com.hust.identityservice.model.Group;

@Getter
@Setter
@Builder
public class SecGroupCache {
    private int childLevel;
    private Long groupId;

    public static SecGroupCache from(Group group) {
        return SecGroupCache.builder()
                .groupId(group.getGroupId())
                .childLevel(group.getChildLevel())
                .build();
    }
}
