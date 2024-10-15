package ca.avengers.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private Long id;
    private Long accountId;
    private String transactionType;
    private BigDecimal amount;
    private String description;
    private String source;
    private String senderName;
    private String senderAccountNumber;
    private String SenderEmail;
    private String status;
    private String category;
    private String currency;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
