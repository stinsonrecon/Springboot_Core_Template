package vn.com.hust.identityservice.message.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogResponse {
    @Id
    @Column(name="LOG_SEND_ID")
    private Long logSendId;

    @Column(name="METHOD")
    private String method;

    @Column(name="API_PARTNER_ID")
    private Long apiPartnerId;

    @Column(name="REQUEST_HEADER")
    private String requestHeader;

    @Column(name="REQUEST_BODY")
    private String requestBody;

    @Column(name="RESPONSE_STATUS")
    private String responseStatus;

    @Column(name="RESPONSE_BODY")
    private String responseBody;

    @Column(name="PROCESS_TIME")
    private String processTime;

    @Column(name="CREATE_TIME")
    private Date createDate;

    @Column(name="URL")
    private String url;

    @Column(name="RETRY")
    private Long retry;

    @Column(name="DEVICE_ID")
    private String deviceId;

    @Column(name="MESSAGE_ID")
    private String messageId;

    @Column(name="SOCIAL_ACCOUNT_ID")
    private Long socialAccountId;

    @Column(name="MAIN_ACCOUNT_ID")
    private String mainAccountId;

    @Column(name="API_PARTNER_NAME")
    private String apiPartnerName;

    @Column(name="RESPONSE_TIME")
    private Date responseDate;

    @Transient
    private List<LogResponse> items = new ArrayList<>();
    @Transient
    private Long total;

}
