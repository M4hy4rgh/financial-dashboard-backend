package ca.avengers.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {

    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String userId;
    private String currency;
}
