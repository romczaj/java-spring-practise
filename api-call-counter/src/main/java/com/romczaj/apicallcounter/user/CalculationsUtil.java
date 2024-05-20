package com.romczaj.apicallcounter.user;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.math.RoundingMode.HALF_UP;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculationsUtil {

    public static Double countUserData(Integer followers, Integer publicRepos) {
        if (followers.equals(0)) {
            return null;
        }

        //  6/followers * (2 + publicRepos) = a * b
        //  a = 6/followers
        BigDecimal a = new BigDecimal(6).divide(new BigDecimal(followers), 4 , HALF_UP);

        //  b = (2 + publicRepos)
        BigDecimal b = new BigDecimal(publicRepos).add(new BigDecimal(2));

        return a.multiply(b).doubleValue();
    }
}
