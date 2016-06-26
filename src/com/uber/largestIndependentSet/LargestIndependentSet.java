package com.uber.largestIndependentSet;

import java.util.*;

public class LargestIndependentSet {

    private Map<Node, Integer> index = new HashMap<Node, Integer>();
    private Map<Integer, Node> nodes = new HashMap<Integer, Node>();

    private int nodeCount;
    private int[][] dp;
    private boolean[][] vis;
    private Set<Node> result;

    public LargestIndependentSet() {
        this.index = new HashMap<Node, Integer>();
    }

    public Set<Node> getLargestIndependentSet(Node root) {
        init();
        assignNodeIndices(root);
        int bestSetSize = solve(root, 0);
        reconstruct(root, 0);
        return result;
    }

    private void reconstruct(Node root, int parentUsed) {
        if (root == null)
            return;

        Node left = root.getLeft();
        Node right = root.getRight();

        if (parentUsed == 1) {
            reconstruct(left, 0);
            reconstruct(right, 0);
        }
        else {
            int resultWithParentNotUsed = solve(left, 0) + solve(right, 0);
            int resultWithParentUsed = 1 + solve(left, 1) + solve(right, 1);
            if (resultWithParentNotUsed > resultWithParentUsed) {
                reconstruct(left, 0);
                reconstruct(right, 0);
            }
            else {
                result.add(root);
                reconstruct(left, 1);
                reconstruct(right, 1);
            }
        }
    }

    private int solve(Node root, int parentUsed) {
        if (root == null)
            return 0;

        int nodeNo = index.get(root);
        if (vis[nodeNo][parentUsed]) return dp[nodeNo][parentUsed];
        vis[nodeNo][parentUsed] = true;

        int best = 0;

        Node left = root.getLeft();
        Node right = root.getRight();

        if (parentUsed == 1) {
            best = solve(left, 0) + solve(right, 0);
        }
        else {
            int resultWithParentNotUsed = solve(left, 0) + solve(right, 0);
            int resultWithParentUsed = 1 + solve(left, 1) + solve(right, 1);
            best = Math.max(resultWithParentNotUsed, resultWithParentUsed);
        }
        return dp[nodeNo][parentUsed] = best;
    }

    private void init() {
        index.clear();
        nodes.clear();
        nodeCount = 0;
        result = new HashSet<Node>();
    }

    private void assignNodeIndices(Node root) {

        go(root);
        dp = new int[nodeCount][2];
        vis = new boolean[nodeCount][2];
    }

    private void go(Node root) {
        if (root == null) return;
        index.put(root, nodeCount);
        nodes.put(nodeCount, root);
        nodeCount++;
        go(root.getLeft());
        go(root.getRight());
    }
}
