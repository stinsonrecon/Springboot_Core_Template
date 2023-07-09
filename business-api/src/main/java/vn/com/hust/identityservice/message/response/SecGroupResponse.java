package vn.com.hust.identityservice.message.response;

import lombok.Builder;
import lombok.Data;
import vn.com.hust.admin.data.CommonListData;

import java.util.List;

@Data
@Builder
public class SecGroupResponse {
    private String code;
    private String message;
    private String status;
    private List<? extends Object> body;

    public static SecGroupResponse from(CommonListData data) {
        return SecGroupResponse.builder()
                .code(data.getCode())
                .message(data.getMessage())
                .body(data.getData())
                .build();
    }
}
