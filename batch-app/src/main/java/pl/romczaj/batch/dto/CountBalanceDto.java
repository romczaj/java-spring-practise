package pl.romczaj.batch.dto;

import lombok.With;

import java.math.BigDecimal;

public record CountBalanceDto(
        String accountNumber,
        BigDecimal income,
        BigDecimal outcome,
        @With BigDecimal balance

) {
}
