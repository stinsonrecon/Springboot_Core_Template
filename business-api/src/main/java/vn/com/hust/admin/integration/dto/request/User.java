package vn.com.hust.admin.integration.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String account;

    private String fullName;

    private Long id;

    private String email;

    private String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Role> roles;

}
