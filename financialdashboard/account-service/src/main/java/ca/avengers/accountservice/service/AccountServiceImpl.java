package ca.avengers.accountservice.service;

import ca.avengers.accountservice.dto.AccountResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final ObjectMapper objectMapper;
    public AccountServiceImpl() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public BigDecimal getBalance(int accountId) {
        log.info("Getting balance for account id: {}", accountId);

        try {
//            List<AccountResponse> accounts = getUserResponseList();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/account_data.json");

            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/account_data.json");
            }
            List<AccountResponse> accounts = objectMapper.readValue(inputStream, new TypeReference<List<AccountResponse>>() {
            });

            AccountResponse account = accounts.stream()
                    .filter(a -> a.getId() == accountId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Account not found for id: " + accountId));

            return account.getBalance();

        } catch (FileNotFoundException e) {
            log.error("File not found", e);
            throw new RuntimeException("File not found", e);
        } catch (IOException e) {
            log.error("Error while reading accounts.json file", e);
            throw new RuntimeException("Error while reading user_data.json file", e);
        }
    }

    @Override
    public AccountResponse getAccountDetails(int accountId) {
        log.info("Getting account details for account id: {}", accountId);

        try {
//            List<AccountResponse> accounts = getUserResponseList();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/account_data.json");

            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/account_data.json");
            }
            List<AccountResponse> accounts = objectMapper.readValue(inputStream, new TypeReference<List<AccountResponse>>() {
            });

            return accounts.stream()
                    .filter(a -> a.getId() == accountId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Account not found for id: " + accountId));

        } catch (FileNotFoundException e) {
            log.error("File not found", e);
            throw new RuntimeException("File not found", e);
        } catch (IOException e) {
            log.error("Error while reading accounts.json file", e);
            throw new RuntimeException("Error while reading user_data.json file", e);
        }
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        log.info("Getting all accounts");

        try {
//            return getUserResponseList();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/account_data.json");

            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/account_data.json");
            }
            return objectMapper.readValue(inputStream, new TypeReference<List<AccountResponse>>() {
            });

        } catch (FileNotFoundException e) {
            log.error("File not found", e);
            throw new RuntimeException("File not found", e);
        } catch (IOException e) {
            log.error("Error while reading accounts.json file", e);
            throw new RuntimeException("Error while reading user_data.json file", e);
        }
    }

    @Override
    public void updateBalance(int accountId, BigDecimal amount) {
        log.info("Updating balance for account id: {}", accountId);

        try {
//            File accountDataFile = new File("./data/account_data.json");
//            if (!accountDataFile.exists()) {
//                throw new FileNotFoundException("File not found: " + accountDataFile.getAbsolutePath());
//            }
//
//            InputStream inputStream = new FileInputStream(accountDataFile);
//            List<AccountResponse> accounts = objectMapper.readValue(inputStream, new TypeReference<List<AccountResponse>>() {
//            });
//            inputStream.close();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/account_data.json");

            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/account_data.json");
            }

            List<AccountResponse> accounts = objectMapper.readValue(inputStream, new TypeReference<List<AccountResponse>>() {
            });

            AccountResponse account = accounts.stream()
                    .filter(a -> a.getId() == accountId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Account not found for id: " + accountId));

            account.setBalance(account.getBalance().add(amount));
            account.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));

            objectMapper.writeValue(new File("data/account_data.json"), accounts);

        } catch (FileNotFoundException e) {
            log.error("File not found", e);
            throw new RuntimeException("File not found", e);
        } catch (IOException e) {
            log.error("Error while reading accounts.json file", e);
            throw new RuntimeException("Error while reading user_data.json file", e);
        }
    }

    @Override
    public  List<AccountResponse> getAccountByUserId(String userId) {
        log.info("Getting account details for user id: {}", userId);

        try {
//            List<AccountResponse> accounts = getUserResponseList();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/account_data.json");

            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/account_data.json");
            }
            List<AccountResponse> accounts = objectMapper.readValue(inputStream, new TypeReference<List<AccountResponse>>() {
            });

            return accounts.stream()
                    .filter(a -> a.getUserId().equals(userId))
                    .toList();

        } catch (FileNotFoundException e) {
            log.error("File not found", e);
            throw new RuntimeException("File not found", e);
        } catch (IOException e) {
            log.error("Error while reading accounts.json file", e);
            throw new RuntimeException("Error while reading user_data.json file", e);
        }
    }


    //helper methods
    private List<AccountResponse> getUserResponseList() throws IOException {
        File userDataFile = new File("./data/account_data.json");
        if (!userDataFile.exists()) {
            throw new FileNotFoundException("File not found: " + userDataFile.getAbsolutePath());
        }

        InputStream inputStream = new FileInputStream(userDataFile);
        List<AccountResponse> accounts = objectMapper.readValue(inputStream, new TypeReference<List<AccountResponse>>() {
        });
        inputStream.close();

        return accounts;
    }
}
