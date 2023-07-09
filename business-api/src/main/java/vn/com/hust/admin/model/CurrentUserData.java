package vn.com.hust.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentUserData {

    @Id
    @Column(name="USER_ID")
    private String userId;

    @Column(name="USER_NAME")
    private String userName;

    @Column(name="FULL_NAME")
    private String fullName ;

    @Column(name="POSITION_ID")
    private String positionId;

    @Column(name="DEPT_ID")
    private Long deptId ;

    @Column(name="ORG_ID")
    private String orgId;

    @Column(name="NAME")
    private String name;

    @Column(name="SHORT_NAME")
    private String shortName;

    public boolean isEmpty() {
        return this.userId == null || this.userName == null;
    }
}
