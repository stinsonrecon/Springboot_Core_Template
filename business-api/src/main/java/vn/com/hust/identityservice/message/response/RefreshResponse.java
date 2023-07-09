package vn.com.hust.identityservice.message.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshResponse {
    private String accessToken;
    private String refreshToken;
    private Integer accessTokenExpirationSecond;
    private Integer refreshTokenExpirationSecond;
}
