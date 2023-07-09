package vn.com.hust.admin.integration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    private String roleCode;

    private String roleName;

    private String parentCode;

    private String account;

    private String type;

    private String userType;

    private String email;

    private Long appId;

    private String appCode;

    private Long status;

    private Integer page;

    private Integer pageSize;

    private String accountLogin;


    public Role(String roleCode, String account) {
        this.roleCode = roleCode;
        this.account = account;
    }

    public Role(String roleCode) {
        this.roleCode = roleCode;
    }



}
