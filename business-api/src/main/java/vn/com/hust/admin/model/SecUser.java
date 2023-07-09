package vn.com.hust.admin.model;

import lombok.*;
import vn.com.hust.admin.integration.dto.request.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SEC_USER")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecUser implements Serializable {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PASSWORD_EXPIRE_STATUS")
    private String passwordExpireStatus;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "PRIORITY")
    private Long priority;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "PHONE")
    private String phone;

    //@Column(name = "MOBILE")
    //private String mobile;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "LAST_CHANGE_PASSWORD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChangePassword;

    @Column(name = "LAST_BLOCK_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastBlockDate;

    @Column(name = "LOGIN_FAILURE_COUNT")
    private Long loginFailureCount;

    @Column(name = "DEPT_ID")
    private Long deptId;

    //hanx them cot chuc vu
    @Column(name = "POSITION_ID")
    private Long positionId;

    @Column(name = "STAFF_CODE")
    private String staffCode;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    @Transient()
    private String yearOfBirth;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    @JoinTable(name = "SEC_GROUP_USER",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    private List<SecGroup> groups = new ArrayList<>();

    @Transient
    private String referCode;

    @Transient
    private Long orgId;

    @Transient
    private String idNo;

    @Transient
    private Integer totalRecord;


    @Transient
    private List<String> jobTitle;

    public static SecUser from(User data) {
        return SecUser.builder()
                .userName(data.getAccount())
                .fullName(data.getFullName())
//                .userId(data.getId())
                .email(data.getEmail())
                .phone(data.getPhone())
//                .(data.getFullName())
                .build();
    }

}
