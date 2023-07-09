package vn.com.hust.identityservice.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.core.common.dto.TreeData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class SiteMapEx extends TreeData {

    @Id
    @Column(name = "MODULE_ID")
    private Long moduleId;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "MODULE_TYPE")
    private String moduleType;

    @Column(name = "MODULE_NAME")
    private String moduleName;

    @Column(name = "PATH")
    private String path;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "ORD")
    private Integer ord;

    @Column(name = "INCLUDE_MENU")
    private Integer includeMenu;

    @Column(name = "APP_ID")
    private Integer appId;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "TRANSLATE_KEY")
    private String translateKey;

    @Column(name = "PATH_FOLDER")
    private String pathFolder;

    @Override
    public Long getLevel() {
        return this.level;
    }

    @Override
    public Long getNodeId() {
        return getModuleId();
    }

    @Override
    public Long getParentNodeId() {
        return getParentId();
    }

    @Override
    public boolean isRoot() {
        return getParentId() == null;
    }
}
