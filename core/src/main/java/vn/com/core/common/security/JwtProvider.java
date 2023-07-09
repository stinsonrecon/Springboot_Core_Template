package vn.com.core.common.security;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.rsa.RSASigner;
import io.fusionauth.jwt.rsa.RSAVerifier;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import vn.com.core.common.config.CoreConstants;
import vn.com.core.common.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;

@Slf4j
@Component("JwtProvider")
@AllArgsConstructor
@NoArgsConstructor
public class JwtProvider {
    protected final String PUBLIC_KEY_FILE = "public_key.pem";
    protected final String PRIVATE_KEY_FILE = "private_key.pem";

    @Autowired
    Environment env;

//    @Value("${path.public-key}")
//    private String pathPublicKey;
//
//    @Value("${public-key-admin-app}")
//    private String publicKeyAdminApp;


    private Clock clock = DefaultClock.INSTANCE;

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(env.getProperty("app.jwt-secret", ""))
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(env.getProperty("app.jwt-secret", "")).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    public String getStringKey(String keyName) {
        String data = "";
        ClassPathResource cpr = new ClassPathResource(keyName);
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.warn("IOException", e);
        }
        return data;
    }

    // doc public key tu path la tham so
    public String getStringKey2(String keyName) {
        String data = "";
        try {
            // lay tu variable fix cung public key
            String pathPublicKey =  env.getProperty("path.public-key", "");
            String publicKeyAdminApp =  env.getProperty("public-key-admin-app", "");

            if ("variable".equalsIgnoreCase(pathPublicKey)) {
                data = publicKeyAdminApp;
            }

            // lay tu path server file public_key.pem
            else {
                File file = new File(pathPublicKey + "/public_key.pem");
                byte[] bdata = Files.readAllBytes(file.toPath());
                data = new String(bdata, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            log.warn("IOException", e);
        }
        log.info("public Key: {}", data);
        return data;
    }

    public String generateAccessTokenRSA(String username, Long userId, String orgId, Long deptId, Set<String> groups,
                                         String timeOut, String mainId) {
        Signer signer = null;
        try {
            signer = RSASigner.newSHA256Signer(getStringKey(PRIVATE_KEY_FILE));
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        JWT jwt = new JWT()
                .setIssuedAt(now)
                .setSubject(username)
                .setExpiration(
                        now.plusSeconds(
                                Integer.parseInt(
                                        StringUtil.nvl(
                                                timeOut, (
                                                        Objects.isNull(mainId) ?
                                                                env.getProperty("app.jwt-expiration", "1800") : "10080"
                                                )
                                        )
                                )
                        )
                )
                .addClaim(CoreConstants.CLAIM_KEY.USER_ID, userId)
                .addClaim(CoreConstants.CLAIM_KEY.USER_NAME, username)
                .addClaim(CoreConstants.CLAIM_KEY.ORG_ID, orgId)
                .addClaim(CoreConstants.CLAIM_KEY.GROUPS, groups)
                .addClaim(CoreConstants.CLAIM_KEY.DEPT_ID, deptId)
                .addClaim(CoreConstants.CLAIM_KEY.MAIN_ID, mainId);
        return JWT.getEncoder().encode(jwt, signer);
    }

    public String generateAccessTokenRSA2(String username, Long userId,
                                          String timeOut, String mainId) {
        Signer signer = null;
        try {
            signer = RSASigner.newSHA256Signer(getStringKey(PRIVATE_KEY_FILE));
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        JWT jwt = new JWT()
                .setIssuedAt(now)
                .setSubject(username)
                .setExpiration(
                        now.plusSeconds(
                                Integer.parseInt(
                                        StringUtil.nvl(
                                                timeOut, (
                                                        Objects.isNull(mainId) ?
                                                                env.getProperty("app.jwt-expiration", "1800") : "10080"
                                                )
                                        )
                                )
                        )
                )
                .addClaim(CoreConstants.CLAIM_KEY.USER_ID, userId)
                .addClaim(CoreConstants.CLAIM_KEY.USER_NAME, username)
                .addClaim(CoreConstants.CLAIM_KEY.MAIN_ID, mainId);
        return JWT.getEncoder().encode(jwt, signer);
    }

    public String generateAccessTokenRSAForGame(String campaignGameId, String gameId, String phone, String timeOut) {
        Signer signer = null;
        try {
            signer = RSASigner.newSHA256Signer(getStringKey(PRIVATE_KEY_FILE));
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        JWT jwt = new JWT()
                .setIssuedAt(now)
                .setSubject(phone)
                .setExpiration(now.plusSeconds(
                        Integer.valueOf(StringUtil.nvl(timeOut, env.getProperty("app.jwt-expiration", "1800")))
                                .intValue()))
                .addClaim("campaignGameId", campaignGameId)
                .addClaim("gameId", gameId)
                .addClaim("clientPhone", phone);
        String encodedJWT = JWT.getEncoder().encode(jwt, signer);
        return encodedJWT;
    }

    public String generateRefreshToken(String token) {
        return Jwts.builder()
                .setSubject(getUserNameFromTokenRSA(token))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + Integer.valueOf(
                        env.getProperty("app.jwt-refresh-expiration", "1200")).intValue() * 1000))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("app.jwt-secret", ""))
                .compact();
    }

    public String generateRefreshToken(String token, String timeRefresh) {
        return Jwts.builder()
                .setSubject(getUserNameFromTokenRSA(token))
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        (new Date()).getTime() + Integer.valueOf(StringUtil.nvl(timeRefresh, "1200"))
                                .intValue() * 1000))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("app.jwt-secret", ""))
                .compact();
    }

    private Object getValueInClaims(String token, String key) {
        Map<String, Object> claims = getAllClaims(token);
        if (claims.containsKey(key)) {
            return claims.get(key);
        }
        return null;
    }

    public String getUserNameFromTokenRSA(String token) {
        return StringUtil.nvl(getValueInClaims(getJwtRaw(token), CoreConstants.CLAIM_KEY.USER_NAME_2), "");
    }

    public String getUserIdFromTokenRSA(String token) {
        return StringUtil.nvl(getValueInClaims(getJwtRaw(token), CoreConstants.CLAIM_KEY.SUB), "0");
    }

    public String getMainIdFromTokenRSA(String token) {
        return StringUtil.nvl(getValueInClaims(getJwtRaw(token), CoreConstants.CLAIM_KEY.MAIN_ID), "");
    }

    public String getdeptIdFromTokenRSA(String token) {
        return StringUtil.nvl(getValueInClaims(getJwtRaw(token), CoreConstants.CLAIM_KEY.DEPT_ID), "0");
    }

    public Long getDeptIdFromTokenRSA(String token) {
        return Long.parseLong(
                StringUtil.nvl(getValueInClaims(getJwtRaw(token), CoreConstants.CLAIM_KEY.DEPT_ID), "0"));
    }

    public Long getOrgIdFromTokenRSA(String token) {
        return Long.parseLong(
                StringUtil.nvl(getValueInClaims(getJwtRaw(token), CoreConstants.CLAIM_KEY.ORG_ID), "0"));
    }

    public ArrayList<String> getLstGroupFromTokenRSA(String token) {
        return (ArrayList<String>) getAllClaims(getJwtRaw(token)).get(CoreConstants.CLAIM_KEY.GROUPS);
    }

    public Date getIssuedAtDateFromTokenRSA(String token) {
        return (Date) getAllClaims(token).get("iat");
    }

    public Date getExpirationDateFromTokenRSA(String token) {
        return (Date) getAllClaims(token).get("exp");
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromTokenRSA(token);
        return expiration.before(clock.now());
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        if (lastPasswordReset == null) {
            return false;
        }

        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromTokenRSA(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public int getJwtExpiration() {
        return Integer.valueOf(env.getProperty("app.jwt-expiration", "1800")).intValue();
    }

    public int getJwtRefreshExpiration() {
        return Integer.valueOf(env.getProperty("app.jwt-refresh-expiration", "1200")).intValue();
    }

    public String getJwtRaw(String authorizationHeader) {
        return StringUtil.stringIsNullOrEmty(authorizationHeader) ? null :
                authorizationHeader.replaceAll("Bearer", "").trim();
    }

    public String generateAccessTokenRSAExpiration100Year(String username, String userId, String orgId, Long deptId,
                                                          Set<String> groups) {
        Signer signer = null;
        try {
            signer = RSASigner.newSHA256Signer(getStringKey(PRIVATE_KEY_FILE));
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        JWT jwt = new JWT()
                .setIssuedAt(now)
                .setSubject(username)
                .setExpiration(now.plusYears(100))
                .addClaim(CoreConstants.CLAIM_KEY.USER_ID, userId)
                .addClaim(CoreConstants.CLAIM_KEY.USER_NAME, username)
                .addClaim(CoreConstants.CLAIM_KEY.ORG_ID, orgId)
                .addClaim(CoreConstants.CLAIM_KEY.DEPT_ID, deptId)
                .addClaim(CoreConstants.CLAIM_KEY.GROUPS, groups);

        String encodedJWT = JWT.getEncoder().encode(jwt, signer);
        return encodedJWT;
    }

    public String getTokenAPI(String username) {
        Signer signer = null;
        try {
            signer = RSASigner.newSHA256Signer(getStringKey(PRIVATE_KEY_FILE));
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        JWT jwt = new JWT()
                .setIssuedAt(now)
                .setSubject(username)
                .setExpiration(now.plusSeconds(
                        Integer.valueOf(env.getProperty("app.jwt-expiration", "1800")).intValue()))
                .addClaim(CoreConstants.CLAIM_KEY.USER_NAME, username);
        String encodedJWT = JWT.getEncoder().encode(jwt, signer);
        return encodedJWT;
    }

    public Boolean validateTokenRSA(String authToken) {
        log.debug("Start validateTokenRSA");
        try {
            Verifier verifier = RSAVerifier.newVerifier(getStringKey2(PUBLIC_KEY_FILE));
            JWT.getDecoder().decode(authToken, verifier);
            return true;
        } catch (Exception e) {
            log.error("Invalid token: " + e.getMessage());
        } finally {
            log.debug("End validateTokenRSA");
        }
        return false;
    }

    public Map<String, Object> getAllClaims(String token) {
        log.debug("Start getAllClaims");
        try {
            String publicKey = getStringKey2(PUBLIC_KEY_FILE);
            Verifier verifier = RSAVerifier.newVerifier(publicKey);
            JWT jwt = JWT.getDecoder().decode(token, verifier);
            return jwt.getAllClaims();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            log.debug("End getAllClaims");
        }
        return new HashMap<>();
    }

    public String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }

    protected Date getExpirationDateFromToken(String token, boolean useRSA) {
        if (useRSA) {
            return Date.from(((ZonedDateTime) getAllClaims(token).get(Claims.EXPIRATION)).toInstant());
        } else {
            return getClaimFromToken(token, Claims::getExpiration);
        }
    }

    protected boolean isTokenExpired(String token, boolean useRSA) {
        final Date expiration = getExpirationDateFromToken(token, useRSA);
        return expiration.before(new Date());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    protected Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(env.getProperty("app.jwt-secret", "")).parseClaimsJws(token).getBody();
    }

    public Jwt<Header, Claims> decodeWithoutKey(String token) {
        int i = token.lastIndexOf('.');
        String withoutSignature = token.substring(0, i + 1);
        return Jwts.parser().parseClaimsJwt(withoutSignature);
    }
}
