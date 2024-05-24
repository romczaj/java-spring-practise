package pl.romczaj.batch.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionGeneratorTest {

    @Test
    void shouldReturnCorrectNumberOfTransactions() {
        // given
        TransactionGenerator transactionGenerator = new TransactionGenerator();
        int numberOfTransactions = 1000;

        // when
        int result = transactionGenerator.generate(numberOfTransactions).size();

        // then
        assertEquals(numberOfTransactions, result);
    }

}