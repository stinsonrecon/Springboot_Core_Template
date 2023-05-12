package vn.com.hust.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "admin")
public class Admin {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "link_img")
    private String linkImg;

    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "CREATED_AT", columnDefinition = "datetime default NOW()")
    private Date createdAt;

    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "UPDATED_AT")
    private Date updatedAt;
}
