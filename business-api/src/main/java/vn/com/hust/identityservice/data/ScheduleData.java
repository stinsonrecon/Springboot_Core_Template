package vn.com.hust.identityservice.data;

import lombok.*;
import vn.com.hust.admin.model.SecScheduleDetail;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleData {

    private Integer dayId;
    private String startTime;
    private String endTime;

    public static ScheduleData from(SecScheduleDetail secScheduleDetail) {
        return ScheduleData.builder()
                .dayId(Integer.parseInt(secScheduleDetail.getDayId().toString()))
                .startTime(secScheduleDetail.getStartTime())
                .endTime(secScheduleDetail.getEndTime())
                .build();
    }

}
