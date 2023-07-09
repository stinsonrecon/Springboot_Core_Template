package vn.com.hust.admin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "SEC_APP")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecApp implements Serializable {
    @Id
    @Column(name = "APP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appId;

    @Column(name = "APP_CODE")
    private String appCode;

    @Column(name = "APP_NAME")
    private String appName;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "MANAGE")
    private Long manage;

    @Column(name="LINK_APP")
    private String linkApp;

    @Column(name="DESCRIPTION")
    private String description;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "APP_ID")
//    private List<SecModule> secModules = new ArrayList<>();
    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Transient
    private String listAppCodeException;

    @Transient
    private List<SecFile> listSecFile;

}
