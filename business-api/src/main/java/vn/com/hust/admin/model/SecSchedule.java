package vn.com.hust.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SEC_SCHEDULE")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecSchedule {
    @Id
    @Column(name = "SCHEDULE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(name = "SCHEDULE_NAME")
    private String scheduleName;

    @Column(name = "STATUS")
    private Long status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private List<SecScheduleDetail> secScheduleDetail = new ArrayList<>();
}
