package com.spectrum.assessment.rewards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spectrum.assessment.rewards.controller.RewardsController;
import com.spectrum.assessment.rewards.model.TransactionalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class RewardsApplicationTests {

    @Autowired
    RewardsController rewardsController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testController() throws Exception {

        //Given
        Date newDate = Date.from(LocalDate.of(2022, Month.APRIL, 21).atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<TransactionalRecord> transactionalRecordList = Arrays.asList(TransactionalRecord.builder()
                .transactionDate(newDate)
                .amount(120.00)
                .transactionId("asdf")
                .transactionName("asdfasdfd")
                .customerId("john").build());
        ObjectMapper objectMapper = new ObjectMapper();

        //When
        mockMvc.perform(post("/rewards/calculateRewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionalRecordList)))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"monthReward\":[{\"month\":\"APRIL\",\"dollarAmount\":90}],\"totalReward\":90,\"customerId\":\"john\"}]"));
    }

}
