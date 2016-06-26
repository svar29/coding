package com.uber.largestIndependentSet;

import org.junit.Test;

import java.util.Set;

public class LargestIndependentSetTest {

    @Test
    public void testLargestIndependentSet() {

        Node root = new Node(
                10,
                new Node(
                        20,
                        new Node(40),
                        new Node(
                                50,
                                new Node(80),
                                null
                        )
                ),
                new Node(
                        30,
                        new Node(60),
                        new Node(70)
                )
        );

        LargestIndependentSet largestIndependentSet = new LargestIndependentSet();
        Set<Node> result = largestIndependentSet.getLargestIndependentSet(root);
        System.out.println(result);
    }
}
