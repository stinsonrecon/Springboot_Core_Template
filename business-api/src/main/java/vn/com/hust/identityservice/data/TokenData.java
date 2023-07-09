package vn.com.hust.identityservice.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenData {

    private String accessToken;
    private String refreshToken;
    private Integer accessTokenExpirationSecond;
    private Integer refreshTokenExpirationSecond;
}
