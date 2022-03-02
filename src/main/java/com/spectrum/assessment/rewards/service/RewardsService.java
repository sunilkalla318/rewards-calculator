package com.spectrum.assessment.rewards.service;

import com.spectrum.assessment.rewards.model.MonthReward;
import com.spectrum.assessment.rewards.model.Rewards;
import com.spectrum.assessment.rewards.model.TransactionalRecord;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardsService {

    public List<Rewards> determineRewardsPerCustomer(List<TransactionalRecord> transactions) {
        Map<String, Integer> customerTotalRewardsMap = new HashMap<>();
        //map with month as key and rewards per customer per the given month as value
        Map<String, Map<String, Integer>> monthlyRewardsPerCustomerMap = new HashMap<>();
        //group transactions by month
        Map<Object, List<TransactionalRecord>> monthlyTransactionsMap =
                transactions.stream().collect(Collectors.groupingBy(trans -> extractMonthFromDate(trans.getTransactionDate())));
        //from the monthly grouped transactions, generate relational maps for total rewards and monthly rewards per customer
        generateRelationalMaps(customerTotalRewardsMap, monthlyRewardsPerCustomerMap, monthlyTransactionsMap);
        //Construct the rewards result per customer
        List<Rewards> rewardsList = new ArrayList<>();
        customerTotalRewardsMap.forEach((id, rewardValue) -> {
            List<MonthReward> monthRewards = getMonthlyRewardsPerCustomer(monthlyRewardsPerCustomerMap, id);
            rewardsList.add(Rewards.builder().monthReward(monthRewards).customerId(id).totalReward(rewardValue).build());
        });
        return rewardsList;
    }

    public static List<MonthReward> getMonthlyRewardsPerCustomer(Map<String, Map<String, Integer>> monthlyRewardsPerCustomerMap, String id) {
        List<MonthReward> monthRewards = new ArrayList<>();
        monthlyRewardsPerCustomerMap.forEach((month, rewardsPerCustomer) -> {
            if(rewardsPerCustomer.get(id)!=null) {
                monthRewards.add(MonthReward.builder().month(month).monthlyRewards(rewardsPerCustomer.get(id)).build());
            }
        });
        return monthRewards;
    }

    public static void generateRelationalMaps(Map<String, Integer> customerTotalRewardsMap, Map<String, Map<String, Integer>> monthlyRewardsPerCustomerMap, Map<Object, List<TransactionalRecord>> monthlyTransactionsMap) {
        for (Map.Entry<Object, List<TransactionalRecord>> entry : monthlyTransactionsMap.entrySet()) {
            Object month = entry.getKey();
            List<TransactionalRecord> transactionsList = entry.getValue();
            Map<String, Integer> customerMonthlyRewardsMap = new HashMap<>();
            transactionsList.forEach(transaction -> {
                int rewardPerTransaction = determineRewardsToBeAwarded(transaction.getAmount());
                customerMonthlyRewardsMap.merge(transaction.getCustomerId(), rewardPerTransaction, Integer::sum);
                customerTotalRewardsMap.merge(transaction.getCustomerId(), rewardPerTransaction, Integer::sum);
            });
            monthlyRewardsPerCustomerMap.put((String)month, customerMonthlyRewardsMap);
        }
    }

    public static int determineRewardsToBeAwarded(double dollarAmount) {
        double totalRewards = 0;
        //rounding up the dollar amount for determining absolute rewards - reward only for one full dollar spent
        if (Math.abs(dollarAmount) > 50 && Math.abs(dollarAmount) < 100) {
            totalRewards = ((int) dollarAmount - 50);
        }
        if (Math.abs(dollarAmount) > 100) {
            totalRewards = ((50)) + (((int) dollarAmount - 100) * 2);
        }
        return Double.valueOf(Math.abs(totalRewards)).intValue();
    }

    public static String extractMonthFromDate(Date transactionDate) {
        return Month.of(transactionDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().getMonthValue()).name();
    }
}
