package com.piestimation.service;

import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.piestimation.dto.PiEstimationRequest;

import lombok.extern.slf4j.Slf4j;

import static com.piestimation.util.RandomGenerator.generateRandomPoints;

/**
 * Calculates the approximate value of Pi using Monte Carlo Method Makes use of RandomGenerator to
 * generate random points in the specified range Increments the counter if the point is inside the
 * circle which is then used to calculate estimated value of Pi
 */
@Service
@Slf4j
public class Pi {
  public Double estimate(final PiEstimationRequest request) throws Exception {

    final int totalPoints = request.getTotalPoints();
    final double radius = request.getRadius();

    /**
     * Counter for points inside the circle. LongAdder to cleverly handle increment operation in
     * thread-safe manner.
     */
    LongAdder pointsInsideCircle = new LongAdder();
    try {
      // Validate input parameters
      if (totalPoints <= 0) {
        log.warn("Invalid totalPoints: {}. Pi estimation may not be accurate.", totalPoints);
        throw new IllegalArgumentException("Invalid totalPoints");
      }

      if (radius <= 0) {
        log.warn("Invalid radius: {}. Pi estimation may not be accurate.", radius);
        throw new IllegalArgumentException("Invalid radius");
      }

      log.debug("Estimating Pi with totalPoints: {} and radius: {}", totalPoints, radius);

      // Perform Monte-carlo simulation
      // Run streams in parallel for optimization.
      IntStream.range(0, totalPoints).parallel().forEach(i -> {
        // Generate random coordinates (x, y) within the square bounding the circle
        double x = generateRandomPoints(-radius, radius);
        double y = generateRandomPoints(-radius, radius);

        // Increment the counter by adding 1 if generate co-ordinate is inside the circle
        if (isInsideCircle(x, y, radius)) {
          pointsInsideCircle.add(1);
        }
      });
    } catch (final Exception e) {
      log.error("Error during Pi estimation: {}", e.getMessage());
    }

    log.info("Points inside circle {}", pointsInsideCircle);

    // Calculating the estimated value of pi.
    // pi=4 *(Area of circle/Area of square)
    return 4.0 * (pointsInsideCircle.doubleValue() / totalPoints);
  }

  /**
   * Checks if a point with coordinates (x, y) is inside the circle of a given radius
   * 
   * @return true if the point is inside the circle, false otherwise.
   */
  boolean isInsideCircle(double x, double y, double radius) {
    return Math.hypot(x, y) <= radius;
  }
}
