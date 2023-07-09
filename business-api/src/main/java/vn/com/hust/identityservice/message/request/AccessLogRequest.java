package vn.com.hust.identityservice.message.request;

import lombok.*;
import vn.com.hust.admin.model.SecAccessLog;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogRequest {

    private Long moduleId;

    public SecAccessLog toSecAccessLog(String userId, String ipAddress) {
        return SecAccessLog.builder()
                .accessDate(new Date())
                .userId(userId)
                .moduleId(moduleId)
                .ipAddress(ipAddress)
                .build();
    }

}
