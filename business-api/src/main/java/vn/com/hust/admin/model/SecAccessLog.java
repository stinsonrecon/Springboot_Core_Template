package vn.com.hust.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "SEC_ACCESS_LOG")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecAccessLog implements Serializable {
    private static final long serialVersionUID = -297553281792804396L;

    @Id
    @Column(name = "ACCESS_LOG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessLogId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "MODULE_ID")
    private Long moduleId;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "ACCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accessDate;

    @Column(name = "APP_ID")
    private Long appId;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "AGENT")
    private String agent;
}
