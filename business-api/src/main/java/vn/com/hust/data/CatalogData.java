package vn.com.hust.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.core.common.dto.TreeData;
import vn.com.hust.entity.Catalog;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogData extends TreeData {
    private Long id;

    private String catalogCode;

    private String catalogName;

    private Long parentId;

    private Long status;

    public static CatalogData from(Catalog catalog) {
        return CatalogData.builder()
                .id(catalog.getId())
                .catalogCode(catalog.getCatalogCode())
                .catalogName(catalog.getCatalogName())
                .parentId(catalog.getParentId())
                .status(catalog.getStatus())
                .build();
    }

    @Override
    public Long getLevel() {
        return 0L;
    }

    @Override
    public Long getNodeId() {
        return id;
    }

    @Override
    public Long getParentNodeId() {
        return parentId;
    }

    @Override
    public boolean isRoot() {
        return parentId == null;
    }
}
