package com.techcasita.lambda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PrimeTest {

    @Test
    public void check() {
        assertEquals(1, Prime.check(7).getDivisor());
        assertEquals(3, Prime.check(9).getDivisor());
        assertEquals(7, Prime.check(49).getDivisor());
    }

    @Test
    public void handleRequest() throws Exception {
        final Gson gson = new GsonBuilder().create();

        final String body = "{\"number\": 17}";
        final ByteArrayInputStream bis = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        new Prime().handleRequest(bis, bos, null);
        assertTrue(0 < bos.size());

        final Prime.ResponseJSON envelope = gson.fromJson(bos.toString(), Prime.ResponseJSON.class);
        assertTrue(envelope.body.contains("\"answer\":\"Yes, 17 is a prime number, divisible only by itself and 1\""));
    }
}