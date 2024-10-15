package ca.avengers.transactionservice.service;

import ca.avengers.transactionservice.dto.TransactionResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final ObjectMapper objectMapper;
    ;

    public TransactionServiceImpl() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public TransactionResponse getTransactionByTransactionId(Long transactionId) {
        log.info("Getting transaction details for transaction id: {}", transactionId);

        try {
//            File userDataFile = new File("./data/transaction_data.json");
//
//            if (!userDataFile.exists()) {
//                log.error("File not found");
//                throw new RuntimeException("File not found");
//            }
//
//            InputStream inputStream = new FileInputStream(userDataFile);
//            List<TransactionResponse> transactions = objectMapper.readValue(inputStream, new TypeReference<List<TransactionResponse>>() {
//            });
//            inputStream.close();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/transaction_data.json");

            if (inputStream == null) {
                log.error("File not found");
                throw new RuntimeException("File not found");
            }
            List<TransactionResponse> transactions = objectMapper.readValue(inputStream, new TypeReference<List<TransactionResponse>>() {
            });

            TransactionResponse transaction = transactions.stream()
                    .filter(t -> t.getId().equals(transactionId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Transaction not found"));

            log.info("Returning transaction details {}", transaction);
            return transaction;

        } catch (FileNotFoundException e) {
            log.error("File not found", e);
            throw new RuntimeException("File not found", e);
        } catch (Exception e) {
            log.error("Error while reading transactions.json file", e);
            throw new RuntimeException("Error while reading transactions.json file", e);
        }

    }

    @Override
    public List<TransactionResponse> getAllTransactionByAccountId(Long accountId) {
        log.info("Getting all transactions for account id: {}", accountId);

        try {
//            File userDataFile = new File("./data/transaction_data.json");
//
//            if (!userDataFile.exists()) {
//                log.error("File not found");
//                throw new RuntimeException("File not found");
//            }
//
//            InputStream inputStream = new FileInputStream(userDataFile);
//            List<TransactionResponse> transactions = objectMapper.readValue(inputStream, new TypeReference<List<TransactionResponse>>() {
//            });
//            inputStream.close();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/transaction_data.json");

            if (inputStream == null) {
                log.error("File not found");
                throw new RuntimeException("File not found");
            }
            List<TransactionResponse> transactions = objectMapper.readValue(inputStream, new TypeReference<List<TransactionResponse>>() {
            });

            log.info("Returning a list of transactions {}", transactions);
            return transactions.stream()
                    .filter(t -> t.getAccountId().equals(accountId))
                    .toList();

        } catch (FileNotFoundException e) {
            log.error("File not found", e);
            throw new RuntimeException("File not found", e);
        } catch (Exception e) {
            log.error("Error while reading transactions.json file", e);
            throw new RuntimeException("Error while reading transactions.json file", e);
        }
    }
}
