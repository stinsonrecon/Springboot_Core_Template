package vn.com.hust.identityservice.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginData implements Serializable {

    private static final long serialVersionUID = 9107722484313759101L;

    @Builder.Default
    private String type = "Bearer";

    private String accessToken;
    private String refreshToken;
    private Integer accessTokenExpirationSecond;
    private Integer refreshTokenExpirationSecond;
    private Integer waitingTime;

    private Long userId;
    private String username;
    private Long orgId; // chuyen shop -> org
    private Long deptId; // chuyen shop -> org
    private long remainExpirePassword;
    private String mainId;
}
