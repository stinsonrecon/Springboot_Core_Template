package vn.com.hust.identityservice.message.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.hust.identityservice.data.SiteMapData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteMapResponse {

    private Long id;
    private Long parentId;

    private String to;
    private String label;
    private String appCode;
    private Integer status;
    private Integer ord;
    private String type;
    private Integer includeMenu;
    private String description;

    private String icon;
    private String pathFolder;
    private String translateKey;

    private List<String> rights = new ArrayList<>();
    private List<SiteMapResponse> items = new ArrayList<>();

    public static SiteMapResponse from(SiteMapData siteMapData) {
        return SiteMapResponse.builder()
                .to(siteMapData.getUrl())
                .label(siteMapData.getName())
                .id(siteMapData.getId())
                .parentId(siteMapData.getParentId())
                .appCode(siteMapData.getAppCode())
                .status(siteMapData.getStatus())
                .ord(siteMapData.getOrd())
                .description(siteMapData.getDescription())
                .icon(siteMapData.getIcon())
                .translateKey(siteMapData.getTranslateKey())
                .pathFolder(siteMapData.getPathFolder())
                .type(siteMapData.getType())
                .includeMenu(siteMapData.getIncludeMenu())
                .rights(siteMapData.getRights())
                .items(siteMapData.getItems().stream().map(x -> SiteMapResponse.from(x)).collect(Collectors.toList()))
                .build();
    }
}
