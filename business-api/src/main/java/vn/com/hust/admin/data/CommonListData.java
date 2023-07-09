package vn.com.hust.admin.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonListData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private List<? extends Object> data;
}
