package vn.com.hust.identityservice.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminUserPrinciple implements UserDetails {

    private static final long serialVersionUID = -6654295011118130477L;

    private String userName;
    private String password;
    private String passwordExpireStatus;
    private String fullName;
    private Integer priority;
    private Integer status;
    private String phone;
    private String mobile;
    private String fax;
    private String email;
    private String address;
    private Date lastChangePassword;
    private Date lastBlockDate;
    private Integer loginFailureCount;
    private Integer checkLdap;

    private String userId;
    private String appCode;
    private Long deptId; // chuyen shop -> org
    //private String listShopCode; // khong dung nua
    private String accessToken;
    private String refreshToken;
    private Integer accessTokenExpirationSecond;
    private Integer refreshTokenExpirationSecond;
    private long remainPasswordExpire;
    //private long responsibleManyShop; khong dung nua
    private String authentication2Factor;
    private Collection<? extends GrantedAuthority> authorities;

    private List<SiteMapData> siteMapDataList;

    private String userType;

    public static AdminUserPrinciple from(UserData userData) {
        return AdminUserPrinciple.builder()
                .userId(userData.getUserId())
                .userName(userData.getUserName())
                .password(userData.getPassword())
                .passwordExpireStatus(userData.getPasswordExpireStatus())
                .fullName(userData.getFullName())
                .priority(userData.getPriority())
                .status(userData.getStatus())
                .phone(userData.getPhone())
                .mobile(userData.getMobile())
                .fax(userData.getFax())
                .email(userData.getEmail())
                .address(userData.getAddress())
                .lastChangePassword(userData.getLastChangePassword())
                .lastBlockDate(userData.getLastBlockDate())
                .loginFailureCount(userData.getLoginFailureCount())
                .deptId(userData.getDeptId())
                //.listShopCode(userData.getListdeptId())
                .checkLdap(userData.getCheckLdap())
                .authorities(userData.getGroups().stream().map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList()))
                .siteMapDataList(userData.getSiteMapDataList())
                .remainPasswordExpire(userData.getRemainPasswordExpire())
                //.responsibleManyShop(userData.getResponsibleManyShop() == null ? 0 : userData.getResponsibleManyShop())
                .authentication2Factor(userData.getAuthentication2Factor())
                .userType(userData.getUserType())
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return passwordExpireStatus.equals("0") ? false : true;
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.nonNull(status) && (status != 0);
    }
}
