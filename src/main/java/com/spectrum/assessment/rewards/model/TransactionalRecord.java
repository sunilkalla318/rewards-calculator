package com.spectrum.assessment.rewards.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionalRecord {
    String customerId;
    String transactionName;
    String transactionId;
    double amount;
    Date transactionDate;
}
