package vn.com.hust.identityservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class ScheduleDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SCHEDULE_ID")
    private Long scheduleId;

    @Column(name = "START_TIME")
    private String accessTimeStart;

    @Column(name = "END_TIME")
    private String accessTimeEnd;

    @Column(name = "DAY_ID")
    private Integer dayId;
}
