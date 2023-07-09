package vn.com.hust.identityservice.sqlite.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.com.hust.identityservice.model.GroupModule;

@Getter
@Setter
@Builder
public class SecGroupModuleCache {
    private String path;
    private Long groupId;
    private Long accessType;

    public static SecGroupModuleCache from(GroupModule groupModule) {
        return SecGroupModuleCache.builder()
                .groupId(groupModule.getGroupId())
                .path(groupModule.getPath())
                .accessType(groupModule.getAccessType())
                .build();
    }
}
