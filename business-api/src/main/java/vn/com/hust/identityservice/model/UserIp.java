package vn.com.hust.identityservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserIp implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ROWKEY")
    private String rowKey;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "IP_DETAIL")
    private String ipDetail;

    @Transient
    private Long ipStart;

    @Transient
    private Long ipEnd;
}
