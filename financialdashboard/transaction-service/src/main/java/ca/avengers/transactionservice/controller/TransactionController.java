package ca.avengers.transactionservice.controller;


import ca.avengers.transactionservice.dto.TransactionResponse;
import ca.avengers.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransactionByTransactionId(@PathVariable Long transactionId) {
        TransactionResponse transactionResponse = transactionService.getTransactionByTransactionId(transactionId);

        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getAllTransactionByAccountId(@PathVariable Long accountId) {
        List<TransactionResponse> transactionResponseList = transactionService.getAllTransactionByAccountId(accountId);

        return new ResponseEntity<>(transactionResponseList, HttpStatus.OK);
    }


}
