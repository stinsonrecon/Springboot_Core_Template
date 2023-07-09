package vn.com.hust.admin.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "SEC_FILE")
public class SecFile {
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long fileId;
    @Column(name = "TABLE_REF_ID")
    private String tableRefId;
    @Column(name = "TABLE_NAME")
    private String tableName;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "DATA_FILE")
    private String dataFile;
    @Column(name = "EXTENTION_FILE")
    private Long extentionFile;
    @Column(name = "CREATE_DATE")
    private Date createDate;

}
