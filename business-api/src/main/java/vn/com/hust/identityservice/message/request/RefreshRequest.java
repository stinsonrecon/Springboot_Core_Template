package vn.com.hust.identityservice.message.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequest {
    @NotBlank
    private String refreshToken;
    @NotBlank
    private Long deptId; // chuyen shop -> org
}
