package vn.com.hust.dto;

import lombok.Data;
import java.util.Date;

@Data
public class BankAccountDTO {
    private String bankName;
    private String username;
    private String department;
    private String bankNumber;

    private String fromDate;
    private String toDate;
}
