package vn.com.hust.admin.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String status;
    private String message;
    private Object body;
    private String timeOut;

    public static CommonData successDefault(Object body) {
        return CommonData.builder()
                         .code("API000")
                         .status("SUCCESS")
                         .message("")
                         .body(body)
                         .build();
    }

    public static CommonData successWithMessage(Object body, String message) {
        return CommonData.builder()
                         .code("API000")
                         .status("SUCCESS")
                         .message(message)
                         .body(body)
                         .build();
    }

    public static CommonData errorDefault(String message) {
        return CommonData.builder()
                         .code("APP001")
                         .status("ERROR")
                         .message(message)
                         .body(null)
                         .build();
    }

    public static CommonData errorWithCode(String code, String message) {
        return CommonData.builder()
                         .code(code)
                         .status("ERROR")
                         .message(message)
                         .body(null)
                         .build();
    }

    public static CommonData errorWithBody(String code, String message, Object body) {
        return CommonData.builder()
                         .code(code)
                         .status("ERROR")
                         .message(message)
                         .body(body)
                         .build();
    }
}

