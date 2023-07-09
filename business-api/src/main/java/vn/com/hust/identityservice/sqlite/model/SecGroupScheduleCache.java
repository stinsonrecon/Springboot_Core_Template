package vn.com.hust.identityservice.sqlite.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.com.hust.identityservice.model.GroupSchedule;

@Getter
@Setter
@Builder
public class SecGroupScheduleCache {
    private Long groupId;
    private String groupName;
    private String accessTimeStart;
    private String accessTimeEnd;

    public static SecGroupScheduleCache from(GroupSchedule groupSchedule) {
        return SecGroupScheduleCache.builder()
                .groupId(groupSchedule.getGroupId())
                .groupName(groupSchedule.getGroupName())
                .accessTimeEnd(groupSchedule.getAccessTimeEnd())
                .accessTimeStart(groupSchedule.getAccessTimeStart())
                .build();
    }
}
