/*
 * PrimeRequest
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 */
package com.techcasita.lambda;

public class PrimeRequest {
    private long number;

    public PrimeRequest() {
    }

    long getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
