package vn.com.hust.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "SEC_MODULE")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecModule {
    @Id
    @Column(name = "MODULE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "MODULE_NAME")
    private String moduleName;

    @Column(name = "PATH")
    private String path;

    @Column(name = "MODULE_TYPE")
    private String moduleType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "ORD")
    private Long ord;

    @Column(name = "INCLUDE_MENU")
    private Long includeMenu;

    @Column(name = "APP_ID")
    private Long appId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "APP_ID", insertable = false, updatable = false)
    private SecApp secApp;

    @OneToMany(mappedBy = "groupId", fetch = FetchType.EAGER)
    private Set<SecGroupModule> secGroupModules;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "TRANSLATE_KEY")
    private String translateKey;

    @Column(name = "PATH_FOLDER")
    private String pathFolder;
}
