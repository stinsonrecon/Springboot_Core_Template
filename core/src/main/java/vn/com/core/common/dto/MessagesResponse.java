package vn.com.core.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import vn.com.core.common.config.ErrorCode;
import vn.com.core.common.exceptions.AppException;
import vn.com.core.common.exceptions.ValidateException;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagesResponse<T> {

    public interface RESPONSE_STATUS {
        String SUCCESS = "SUCCESS";
        String ERROR = "ERROR";
        String ERROR_WITH_PAR = "ERROR_WITH_PAR";
    }

    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] paramCode;
    private String message;
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long elapsedTimeMs;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requestId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer total;
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PagingADM paging;

    public MessagesResponse() {
        this.timestamp = System.currentTimeMillis();
        this.code = ErrorCode.SUCCESS;
        this.status = RESPONSE_STATUS.SUCCESS;
    }

    public MessagesResponse<T> error(Exception e) {
        this.timestamp = System.currentTimeMillis();
        if (e instanceof AppException) {
            this.code = ((AppException) e).getCode();
        } else if (e instanceof ValidateException) {
            this.code = ((ValidateException) e).getCode();
        } else {
            this.code = ErrorCode.SYSTEM_ERROR;
        }
        this.code = ErrorCode.SYSTEM_ERROR;
        this.status = RESPONSE_STATUS.ERROR;
        this.message = e.toString();
        return this;
    }

    public MessagesResponse build(Object obj) {
        MessagesResponse msg = new MessagesResponse();
        if (obj instanceof MessagesResponse) {
            msg = (MessagesResponse) obj;
        } else {
            msg.setData(obj);
        }
        if (msg.getTimestamp() == null) {
            msg.setTimestamp(System.currentTimeMillis());
        }
        if (msg.getData() instanceof List) {
            msg.setTotal(((List) msg.getData()).size());
        } else if (msg.getData() instanceof Map ) {
            msg.setTotal(((Map) msg.getData()).size());
        }
//        msg.setElapsedTimeMs(elapsedTimeMs);
        return msg;
    }
}
