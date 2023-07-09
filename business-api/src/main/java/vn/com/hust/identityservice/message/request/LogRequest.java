package vn.com.hust.identityservice.message.request;

import lombok.Data;

@Data
public class LogRequest {
    private Integer page = 1;

    private Integer pageSize = 10;

    private String fromDate;

    private String toDate;

    private String apiPartnerId;

    private String responseStatus;

    private String messageId;
}
