package pl.romczaj.batch.domain.transaction;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {

    @Query(value = """
            SELECT account as accountNumber, SUM(outcome) AS outcome, SUM(income) AS income
            FROM (
                SELECT sender_account AS account,
                       amount AS outcome,
                       0 AS income
                FROM user_transaction
                UNION ALL
                SELECT receiver_account AS account,
                       0 AS outcome,
                       amount AS income
                FROM user_transaction
            ) AS transactions
            GROUP BY account
            """, nativeQuery = true)
    List<Tuple> calculateAccountBalances();
}
