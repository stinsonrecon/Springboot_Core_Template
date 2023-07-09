package vn.com.hust.identityservice.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.core.common.utils.StringUtil;
import vn.com.hust.admin.model.SecGroup;
import vn.com.hust.admin.model.SecUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData implements Serializable {

    private static final long serialVersionUID = -4969710621515849143L;
    private String userId;
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
    private Long deptId; // chuyen shop -> org
    private Integer checkLdap;

    private List<String> groups;
    private List<SiteMapData> siteMapDataList;
    private List<ScheduleData> scheduleData;
    private long remainPasswordExpire;
    private String authentication2Factor;
    private String userType;

    public static UserData from(SecUser secUser) {
        return UserData.builder()
                .userId(String.valueOf(secUser.getUserId()))
                .userName(secUser.getUserName())
                .password(secUser.getPassword())
                .passwordExpireStatus(secUser.getPasswordExpireStatus())
                .fullName(secUser.getFullName())
//                .priority(Integer.parseInt(secUser.getPriority().toString()))
                .status(Objects.nonNull(secUser.getStatus()) ? Integer.parseInt(secUser.getStatus().toString()) : null)
                .phone(secUser.getPhone())
                .mobile(secUser.getMobile())
                .fax(secUser.getFax())
                .email(secUser.getEmail())
                .address(secUser.getAddress())
                .lastChangePassword(secUser.getLastChangePassword())
                .lastBlockDate(secUser.getLastBlockDate())
                .loginFailureCount(Integer.parseInt(StringUtil.nvl(secUser.getLoginFailureCount(), "0")))
                .deptId(secUser.getDeptId())
                .groups(secUser.getGroups().stream().map(SecGroup::getGroupName).collect(Collectors.toList()))
                .build();
    }
}
