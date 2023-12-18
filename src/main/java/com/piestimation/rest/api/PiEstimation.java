
package com.piestimation.rest.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piestimation.dto.PiEstimationRequest;
import com.piestimation.service.Pi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/piestimation")
@RequiredArgsConstructor
@Slf4j
public class PiEstimation {
  private final Pi pi;

  /**
   * End point for estimating Pi using the Monte-Carlo method.
   *
   * @request The PiEstimationRequest contains input parameters for estimation.
   * @throws Exception If there is an error during Pi estimation or input parameters are invalid.
   * @return The estimated value of Pi.
   * @see PiEstimationTests#estimatePiWithLargeDataSet()
   */
  @PostMapping("/monte-carlo")
  public double estimatePi(@RequestBody PiEstimationRequest request) throws Exception {
    log.info("Starting estimation of pi using monte-carlo method");
    return pi.estimate(request);
  }
}
