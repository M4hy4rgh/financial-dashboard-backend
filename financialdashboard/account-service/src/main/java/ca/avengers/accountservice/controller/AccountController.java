package ca.avengers.accountservice.controller;


import ca.avengers.accountservice.dto.AccountResponse;
import ca.avengers.accountservice.dto.BalanceUpdateRequest;
import ca.avengers.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountResponse> getAccountDetails(@PathVariable int accountId) {
        AccountResponse accountResponse = accountService.getAccountDetails(accountId);
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping("/{accountId}/balance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BigDecimal> getBalance(@PathVariable int accountId) {
        BigDecimal balance = accountService.getBalance(accountId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> accountResponses = accountService.getAllAccounts();
        return ResponseEntity.ok(accountResponses);
    }

    @PutMapping("/{accountId}/balance")
    public ResponseEntity<Void> updateBalance(@PathVariable int accountId, @RequestBody BalanceUpdateRequest request) {
        accountService.updateBalance(accountId, request.getAmount());
        return ResponseEntity.ok().build();
    }




}
