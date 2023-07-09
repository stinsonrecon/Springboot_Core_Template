package vn.com.hust.identityservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class GroupSchedule {

    @Id
    @Column(name = "ROWKEY")
    private String rowKey;

    @Column(name = "GROUP_ID")
    private Long groupId;

    @Column(name = "GROUP_NAME")
    private String groupName;

    @Column(name = "ACCESS_TIME_START")
    private String accessTimeStart;

    @Column(name = "ACCESS_TIME_END")
    private String accessTimeEnd;

    @Column(name = "DAY_ID")
    private Integer dayId;
}
