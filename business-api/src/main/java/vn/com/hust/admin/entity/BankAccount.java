package vn.com.hust.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "bank_account")
public class BankAccount {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "username")
    private String username;

    @Column(name = "department")
    private String department;

    @Column(name = "bank_number")
    private String bank_number;

    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "CREATED_AT", columnDefinition = "datetime default NOW()")
    private Date createdAt;

    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Transient
    private String createdAtString;

    @Transient
    private String updatedAtString;
}
