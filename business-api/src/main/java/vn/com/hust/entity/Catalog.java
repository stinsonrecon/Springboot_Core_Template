package vn.com.hust.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "catalog")
public class Catalog {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "catalog_code")
    private String catalogCode;

    @Column(name = "catalog_name")
    private String catalogName;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "status")
    private Long status;

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

    @Transient
    private Long level;
}
