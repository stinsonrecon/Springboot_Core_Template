package vn.com.hust.admin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.com.core.common.utils.DateUtil;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author TienLM20
 * @since 09/07/2023
 */

@Data
@Entity
@Table(name = "sec_department")
public class SecDepartment {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPT_ID")
    private Long deptId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DEPART_NAME_SHORT")
    private String departNameShort;

    @Column(name = "END_DATE")
    @JsonFormat(pattern = DateUtil.FORMAT_DATE_TIME2)
    private LocalDateTime endDate;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "START_DATE")
    @JsonFormat(pattern = DateUtil.FORMAT_DATE_TIME2)
    private LocalDateTime startDate;

    @Column(name = "STATUS")
    private String status;
}
