package vn.com.hust.identityservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "PASSWORD_EXPIRE_STATUS")
    private String passwordExpireStatus;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "LOGIN_FAILURE_COUNT ")
    private Integer loginFailureCount;

    //B1_EVN
    @Column(name = "POSITION_ID")
    private Long positionId;
}
