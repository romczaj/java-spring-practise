package com.romczaj.apicallcounter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "call_counter")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Access(AccessType.FIELD)
public class CallCounterEntity {

    @Id
    private String login;
    private Long requestCount;

    public void updateRequestCount(Long value) {
        requestCount = value;
    }
}
