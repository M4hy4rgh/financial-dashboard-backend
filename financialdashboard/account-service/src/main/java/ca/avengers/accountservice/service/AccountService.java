package ca.avengers.accountservice.service;

import ca.avengers.accountservice.dto.AccountResponse;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    public BigDecimal getBalance(int accountId);

    public void updateBalance(int accountId, BigDecimal amount);

    public AccountResponse getAccountDetails(int accountId);

    public List<AccountResponse> getAllAccounts();

}
