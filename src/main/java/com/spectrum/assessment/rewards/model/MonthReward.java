package com.spectrum.assessment.rewards.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthReward {
    String month;
    Integer monthlyRewards;
}
