package ca.avengers.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    private Long accountId;
    private String transactionType;
    private BigDecimal amount;
    private String description;
    private String source;
    private String senderName;
    private String senderAccountNumber;
    private String senderEmail;
    private String status;
    private String category;
    private String currency;
}
