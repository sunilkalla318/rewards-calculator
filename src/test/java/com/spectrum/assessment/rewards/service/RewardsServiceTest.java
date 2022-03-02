package com.spectrum.assessment.rewards.service;

import com.spectrum.assessment.rewards.model.MonthReward;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RewardsServiceTest {

    private static RewardsService rewardsService;

    @BeforeAll
    public static void setUp(){
        rewardsService = new RewardsService();
    }

    @Test
    void testMonthExtraction(){
        //Given
        Date newDate = Date.from(LocalDate.of(2022, Month.APRIL, 21).atStartOfDay(ZoneId.systemDefault()).toInstant());

        //When
        String monthOfTheYear = RewardsService.extractMonthFromDate(newDate);

        //Then
        assertEquals("APRIL", monthOfTheYear);
        assertNotEquals("MARCH", monthOfTheYear);
    }

    @Test
    void testDetermineRewardsToBeAwarded(){
        //Given
        double dollarAmount = 120.00;

        //When
        int rewards = RewardsService.determineRewardsToBeAwarded(dollarAmount);

        //Then
        assertEquals(90, rewards);
        assertNotEquals(80, rewards);
    }

    @Test
    void testGetMonthlyRewardsPerCustomer(){
        //Given
        Map<String, Map<String, Integer>> monthlyRewardsPerCustomerMap = new HashMap<>();
        Map<String, Integer> customerRewardsJanMap = new HashMap<>();
        customerRewardsJanMap.put("don", 30);
        customerRewardsJanMap.put("jon", 50);
        customerRewardsJanMap.put("sam", 110);
        Map<String, Integer> customerRewardsAprilMap = new HashMap<>();
        customerRewardsAprilMap.put("don", 50);
        customerRewardsAprilMap.put("jon", 50);
        customerRewardsAprilMap.put("sam", 110);
        customerRewardsAprilMap.put("ted", 130);
        monthlyRewardsPerCustomerMap.put("JANUARY", customerRewardsJanMap);
        monthlyRewardsPerCustomerMap.put("APRIL", customerRewardsAprilMap);
        String customerId = "don";

        //When
        List<MonthReward> monthRewards = RewardsService.getMonthlyRewardsPerCustomer(monthlyRewardsPerCustomerMap, customerId);

        //Then
        assertEquals(monthRewards.get(0).getMonth(), "JANUARY");
        assertEquals(monthRewards.get(0).getMonthlyRewards(), 30);
        assertEquals(monthRewards.get(1).getMonth(), "APRIL");
        assertEquals(monthRewards.get(1).getMonthlyRewards(), 50);

    }

}
