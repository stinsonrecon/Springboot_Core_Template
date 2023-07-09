package vn.com.hust.identityservice.data;

import lombok.*;
import vn.com.core.common.utils.ValidationUtil;
import vn.com.hust.admin.model.SecModule;
import vn.com.hust.identityservice.model.SiteMapEx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteMapData implements Serializable {

    private static final long serialVersionUID = -3042351945281103652L;

    private Long id;
    private Long parentId;

    private String url;
    private String name;
    private String appCode;
    private Integer status;
    private Integer ord;
    private String type;
    private Integer includeMenu;
    private String description;

    private String icon;
    private String pathFolder;
    private String translateKey;

    private List<SiteMapData> items = new ArrayList<>();
    private List<String> rights = new ArrayList<>();

    public static SiteMapData from(SecModule secModule) {
        return SiteMapData.builder()
                .id(secModule.getModuleId())
                .parentId(secModule.getParentId())
                .url(secModule.getPath())
                .name(secModule.getModuleName())
                .appCode(secModule.getSecApp().getAppCode())
                .status(Integer.parseInt(secModule.getStatus().toString()))
                .ord(Integer.parseInt(secModule.getOrd().toString()))
                .type(secModule.getModuleType())
                .description(secModule.getDescription())
                .icon(secModule.getIcon())
                .translateKey(secModule.getTranslateKey())
                .pathFolder(secModule.getPathFolder())
                .includeMenu(Integer.parseInt(secModule.getIncludeMenu().toString()))
                .rights(new ArrayList<>())
                .items(new ArrayList<>())
                .build();
    }

    public static SiteMapData from(SiteMapEx siteMapEx, String appCode) {
        return SiteMapData.builder()
                .id(siteMapEx.getModuleId())
                .parentId(siteMapEx.getParentId())
                .url(siteMapEx.getPath())
                .name(siteMapEx.getModuleName())
                .appCode(appCode)
                .status(siteMapEx.getStatus())
                .ord(siteMapEx.getOrd())
                .type(siteMapEx.getModuleType())
                .includeMenu(siteMapEx.getIncludeMenu())
                .rights(new ArrayList<>())
                .description(siteMapEx.getDescription())
                .icon(siteMapEx.getIcon())
                .translateKey(siteMapEx.getTranslateKey())
                .pathFolder(siteMapEx.getPathFolder())
                .items((!ValidationUtil.isNullOrEmpty(siteMapEx.getChildren()))
                        ? (List)siteMapEx.getChildren().stream()
                        .map(x -> SiteMapData.from((SiteMapEx) x, appCode))
                        .collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    public void addChild(SiteMapData child) {
        if (!this.items.contains(child) && child != null)
            this.items.add(child);
    }
}
