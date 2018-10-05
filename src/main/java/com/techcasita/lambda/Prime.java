/*
 * Prime, a very basic AWS Lambda Function
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 */
package com.techcasita.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Lambda request handlers implement AWS Lambda Function application logic,
 * using plain old java objects as input and output.
 * However, since input and output is JSON encoded, AWS Lambda will perform (de-)serialization.
 */
public class Prime implements RequestStreamHandler {
    private static final Logger Log = LogManager.getLogger(Prime.class);

    /**
     * Check if the provided number is evenly divisible only by itself and one.
     *
     * @param n {@link Long}
     * @return {@link boolean} true, if the provided long is a prime number
     */
    static PrimeResponse check(final long n) {
        if (n < 2) {
            return new PrimeResponse();
        }
        if (n == 2 || n == 3) {
            return new PrimeResponse(n);
        }
        if (n % 2 == 0) {
            return new PrimeResponse(n, 2);
        }
        if (n % 3 == 0) {
            return new PrimeResponse(n, 3);
        }
        final long sqrtN = (long) Math.sqrt(n) + 1;
        for (long i = 6; i <= sqrtN; i += 6) {
            if (n % (i - 1) == 0) {
                return new PrimeResponse(n, i - 1);
            }
            if (n % (i + 1) == 0) {
                return new PrimeResponse(n, i + 1);
            }

        }
        return new PrimeResponse(n);
    }

    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input stream
     * @param output  The Lambda function output stream
     * @param context The Lambda execution environment context object.
     * @throws IOException e.g. network unavailable
     */
    @Override
    public void handleRequest(final InputStream input, final OutputStream output, final Context context) throws IOException {
        try {
            final Gson gson = new Gson();
            final PrimeRequest request = gson.fromJson(new InputStreamReader(input), PrimeRequest.class);
            final PrimeResponse response = Prime.check(request.getNumber());
            output.write(new ResponseJSON("200", gson.toJson(response)).toString().getBytes());
        } catch (JsonParseException ex) {
            Log.error(ex.toString());
            output.write(new ResponseJSON("400", "Marshalling Error: " + input.toString()).toString().getBytes());
        }
        output.close();
    }

    @SuppressWarnings("unused")
    static class ResponseJSON {
        final boolean isBase64Encoded = false;
        final String headers = "";
        final String statusCode;
        final String body;

        ResponseJSON(final String statusCode, final String body) {
            this.statusCode = statusCode;
            this.body = body;
        }

        @Override
        public String toString() {
            return new GsonBuilder().create().toJson(this);
        }
    }
}
