package com.piestimation.rest.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piestimation.dto.PiEstimationRequest;
import com.piestimation.service.Pi;

import lombok.extern.slf4j.Slf4j;

@ComponentScan(basePackages = "com.piestimation.application")
@SpringBootTest(classes = {PiEstimation.class, Pi.class})
@AutoConfigureMockMvc
@Slf4j
class PiEstimationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void estimatePiWithSmallDataSet() throws Exception {
    PiEstimationRequest request =
        PiEstimationRequest.builder().radius(5.0).totalPoints(100).build();

    MvcResult mvc = mockMvc
        .perform(MockMvcRequestBuilders.post("/piestimation/monte-carlo")
            .contentType("application/json").content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String content = mvc.getResponse().getContentAsString();
    log.info("Estimated value of Pi is {}", content);

  }

  @Test
  void estimatePiWithLargeDataSet() throws Exception {
    PiEstimationRequest request =
        PiEstimationRequest.builder().radius(10.0).totalPoints(1000000).build();

    MvcResult mvc = mockMvc
        .perform(MockMvcRequestBuilders.post("/piestimation/monte-carlo")
            .contentType("application/json").content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String content = mvc.getResponse().getContentAsString();
    log.info("Estimated value of Pi is {}", content);

  }

  @Test
  void estimatePiWithInvalidRadius() throws Exception {
    PiEstimationRequest request =
        PiEstimationRequest.builder().radius(-1.2).totalPoints(100000).build();

    MvcResult mvc = mockMvc
        .perform(MockMvcRequestBuilders.post("/piestimation/monte-carlo")
            .contentType("application/json").content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();

    int statusCode = mvc.getResponse().getStatus();
    log.info("Status is {}", statusCode);
  }

  @Test
  void estimatePiWithInvalidTotalPoints() throws Exception {
    PiEstimationRequest request = PiEstimationRequest.builder().radius(2.8).totalPoints(0).build();

    MvcResult mvc = mockMvc
        .perform(MockMvcRequestBuilders.post("/piestimation/monte-carlo")
            .contentType("application/json").content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();

    int statusCode = mvc.getResponse().getStatus();
    log.info("Status is {}", statusCode);
  }

}
