package vn.com.hust.identityservice.message.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import vn.com.hust.admin.model.SecAccessLog;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessLogResponse {

    private Long accessLogId;
    private String userId;
    private Long moduleId;
    private String ipAddress;
    @JsonFormat(timezone = "GMT+07:00", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date accessDate;

    public static AccessLogResponse from(SecAccessLog secAccessLog) {
        return AccessLogResponse.builder()
                .userId(secAccessLog.getUserId())
                .moduleId(secAccessLog.getModuleId())
                .ipAddress(secAccessLog.getIpAddress())
                .accessDate(secAccessLog.getAccessDate())
                .accessLogId(secAccessLog.getAccessLogId())
                .build();
    }
}
