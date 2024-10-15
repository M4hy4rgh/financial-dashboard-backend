package ca.avengers.transactionservice.service;

import ca.avengers.transactionservice.dto.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    public TransactionResponse getTransactionByTransactionId(Long transactionId);

    public List<TransactionResponse> getAllTransactionByAccountId(Long accountId);

//    public void createTransaction(String transactionId, String accountId, String type, BigDecimal amount);
//    public void updateTransaction(String transactionId, String accountId, String type, BigDecimal amount);
//    public void getTransactionByType(String type);
//    public void getTransactionByAmount(BigDecimal amount);
//    public void getTransactionByDate(String date);
//    public void getTransactionByAccountIdAndType(String accountId, String type);
//    public void getTransactionByAccountIdAndAmount(String accountId, BigDecimal amount);
//    public void getTransactionByAccountIdAndDate(String accountId, String date);
//    public void deleteAllTransactionsForAccount(String accountId);
//    public void getTransactionByAccountIdAndTypeAndAmount(String accountId, String type, BigDecimal amount);
//    public void getTransactionByAccountIdAndTypeAndDate(String accountId, String type, String date);
//    public void getTransactionByAccountIdAndAmountAndDate(String accountId, BigDecimal amount, String date);
//    public void getTransactionByAccountIdAndTypeAndAmountAndDate(String accountId, String type, BigDecimal amount, String date);
}
