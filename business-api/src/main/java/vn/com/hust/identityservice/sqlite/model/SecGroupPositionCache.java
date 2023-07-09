package vn.com.hust.identityservice.sqlite.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.com.hust.identityservice.model.GroupPosition;

@Getter
@Setter
@Builder
public class SecGroupPositionCache {
    private Long groupId;
    private Long positionId;

    public static SecGroupPositionCache from(GroupPosition groupPosition) {
        return SecGroupPositionCache.builder()
                .groupId(groupPosition.getGroupId())
                .positionId(groupPosition.getPositionId())
                .build();
    }
}
