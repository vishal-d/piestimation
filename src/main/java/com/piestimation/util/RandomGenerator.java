package com.piestimation.util;

/**
 * Utility class for generating random numbers within a specified range [Min, Max).
 * 
 * NOTE: Just like any other estimation in statistics monte-carlo also has limitations. Here double
 * is used instead of integer to generate random values for better precision rate. Using the integer
 * would compromise approximation for smaller intervals! Important thing to note here is that the
 * below range would not include max(inclusive) but given the random distribution for larger data
 * sets, it would approximate correctly. For the last bit of correction to reach closest to
 * max(inclusive) Double.MIN_VALUE is added.
 * 
 * @return A random double value within the specified range.
 */
public class RandomGenerator {
  public static double generateRandomPoints(double min, double max) {
    return min + (Math.random() * ((max - min) + Double.MIN_VALUE));
  }
}
