package pl.romczaj.batch.generator;

import pl.romczaj.batch.dto.TransactionInsertDto;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.time.ZoneOffset.UTC;

public class TransactionGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final List<String> ACCOUNT_NUMBERS = List.of(
            "PL61109010140000071219812874",
            "PL61109010140000071219812875",
            "PL61109010140000071219812876",
            "PL61109010140000071219812877",
            "PL61109010140000071219812878",
            "PL61109010140000071219812879",
            "PL61109010140000071219812880",
            "PL61109010140000071219812881",
            "PL61109010140000071219812882",
            "PL61109010140000071219812883"
    );

    private static final List<String> CURRENCIES = List.of("PLN", "EUR", "USD");
    private static final List<String> TRANSACTION_TITLES = List.of(
            "Payment", "Transfer", "Withdrawal",
            "Deposit", "Refund", "Charge",
            "Salary", "Bonus", "Rent",
            "Mortgage", "Utilities", "Subscription"
    );

    private static final int MAX_AMOUNT = 10000;
    private static final int MIN_AMOUNT = 1;
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
    private static final LocalDateTime MIN_DATE = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
    private static final long MAX_DATE_EPOCH = MAX_DATE.toEpochSecond(UTC);
    private static final long MIN_DATE_EPOCH = MIN_DATE.toEpochSecond(UTC);

    public List<TransactionInsertDto> generate(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> generateOne())
                .toList();
    }

    private TransactionInsertDto generateOne() {
        String senderAccount = ACCOUNT_NUMBERS.get(RANDOM.nextInt(ACCOUNT_NUMBERS.size()));
        String receiverAccount = generateAccountNumberExcept(senderAccount);
        Integer amount = RANDOM.nextInt(MAX_AMOUNT - MIN_AMOUNT) + MIN_AMOUNT;
        String currency = CURRENCIES.get(RANDOM.nextInt(CURRENCIES.size()));
        String title = TRANSACTION_TITLES.get(RANDOM.nextInt(TRANSACTION_TITLES.size()));
        Instant transactionDate = generateTransactionDate();

        return new TransactionInsertDto(
                UUID.randomUUID(),
                senderAccount,
                receiverAccount,
                amount,
                currency,
                title,
                transactionDate
        );
    }

    private String generateAccountNumberExcept(String except) {
        String accountNumber = ACCOUNT_NUMBERS.get(RANDOM.nextInt(ACCOUNT_NUMBERS.size()));
        return accountNumber.equals(except) ? generateAccountNumberExcept(except) : accountNumber;
    }

    private Instant generateTransactionDate() {
        long randomEpoch = RANDOM.nextLong(MAX_DATE_EPOCH - MIN_DATE_EPOCH) + MIN_DATE_EPOCH;
        return Instant.ofEpochSecond(randomEpoch);
    }
}
