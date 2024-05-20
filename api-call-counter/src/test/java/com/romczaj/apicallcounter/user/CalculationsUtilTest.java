package com.romczaj.apicallcounter.user;

import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.romczaj.apicallcounter.user.CalculationsUtil.countUserData;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class CalculationsUtilTest {

    @Test
    @DisplayName("Should return null when followers is 0")
    void returnZero() {
        Assertions.assertNull(countUserData(0, 10));
    }

    @ParameterizedTest
    @DisplayName("Should return correct values when inputs are correct")
    @MethodSource("com.romczaj.apicallcounter.user.CalculationsUtilTest#checkMultiArgumentsMethodSource")
    void returnCorrectValues(Integer followers, Integer publicRepos, Double result){
        assertEquals(result, countUserData(followers, publicRepos));
    }

    static Stream<Arguments> checkMultiArgumentsMethodSource() {
        return Stream.of(
            Arguments.of(6, 0, 2.0),
            Arguments.of(60, 4, 0.6),
            Arguments.of(7, 1, 2.5713)
        );
    }
}