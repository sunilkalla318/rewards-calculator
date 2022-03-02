package com.spectrum.assessment.rewards.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rewards {
    List<MonthReward> monthReward;
    int totalReward;
    String customerId;
}
