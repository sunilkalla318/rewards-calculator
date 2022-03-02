package com.spectrum.assessment.rewards.controller;

import com.spectrum.assessment.rewards.model.Rewards;
import com.spectrum.assessment.rewards.model.TransactionalRecord;
import com.spectrum.assessment.rewards.service.RewardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/rewards")
public class RewardsController {
    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService){
        this.rewardsService = rewardsService;
    }

    @PostMapping(value = "/calculateRewards", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Rewards>> determineRewardsMonthlyRewards(@RequestBody List<TransactionalRecord> transactions){

        log.info("Started rewards processing");
        List<Rewards> rewardsList = rewardsService.determineRewardsPerCustomer(transactions);

        return ResponseEntity.status(HttpStatus.OK).body(rewardsList);
    }
}
