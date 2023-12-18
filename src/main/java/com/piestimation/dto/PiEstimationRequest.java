package com.piestimation.dto;

import lombok.Builder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * Represents a request for estimating Pi using the Monte Carlo method.
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PiEstimationRequest {
  private double radius;
  private int totalPoints;
}
