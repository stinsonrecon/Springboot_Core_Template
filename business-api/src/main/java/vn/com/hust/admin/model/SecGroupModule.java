package vn.com.hust.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "SEC_GROUP_MODULE")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecGroupModule implements Serializable {

    @Id
    @Column(name = "ROW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rowId;

    @Column(name = "GROUP_ID")
    private Long groupId;

    @Column(name = "MODULE_ID")
    private Long moduleId;

    @Column(name = "RIGHT_CODE")
    private String rightCode;

    @Column(name = "ACCESS_TYPE")
    private Long accessType;

//    @EmbeddedId
//    private SecGroupModuleId secGroupModuleId = new SecGroupModuleId();
//
//    @Transient
//    public SecGroup getSecGroup() {
//        return getSecGroupModuleId().getSecGroup();
//    }
//
//    @Transient
//    public SecModule getSecModule() {
//        return getSecGroupModuleId().getSecModule();
//    }
}
