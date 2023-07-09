package vn.com.hust.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@Table(name = "SEC_SYSTEM_POLICY")
@NoArgsConstructor
public class SecSystemPolicy implements Serializable {
    @Id
    @Column(name = "POLICY_TYPE")
    private String policyType;

    @Column(name = "POLICY_VALUE")
    private String policyValue;
}
