package vn.com.core.common.dto;

import lombok.Data;

@Data
public class PagingADM {
    private Integer offset;

    private Integer pageSize;

    private Integer totalPage;

    private Integer totalRecord;

}
