package com.uber.fulfilment;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FulfilmentTest {

    @Test
    public void testFulfillment() {
        Fulfilment fulfilment = new Fulfilment();
        List<Integer> requestIdsToFulfill = fulfilment.getPromisesToFulfill(new int[]{1, 1, 1, 1, 1}, 5);
        assertEquals(Arrays.asList(0, 1, 2 , 3, 4), requestIdsToFulfill);

        requestIdsToFulfill = fulfilment.getPromisesToFulfill(new int[]{1, 2, 4, 8}, 11);
        assertEquals(Arrays.asList(0, 1, 3), requestIdsToFulfill);
    }
}
