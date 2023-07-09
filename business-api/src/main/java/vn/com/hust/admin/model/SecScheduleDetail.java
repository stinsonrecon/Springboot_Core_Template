package vn.com.hust.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "SEC_SCHEDULE_DETAIL")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecScheduleDetail {

    @Column(name = "SCHEDULE_ID")
    private Long scheduleId;

    @Id
    @Column(name = "DAY_ID")
    private Long dayId;

    @Column(name = "START_TIME")
    private String startTime;

    @Column(name = "END_TIME")
    private String endTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SCHEDULE_ID", insertable = false, updatable = false)
    private SecSchedule secSchedule;
}
