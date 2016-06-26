package com.uber.largestIndependentSet;

public class Node {

    private int val;
    private Node left, right;

    public Node(int val) {
        this(val, null, null);
    }

    public Node(int val, Node left, Node right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public int getVal() {
        return val;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (val != node.val) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return val;
    }

    @Override
    public String toString() {
        return "" + val;
    }
}
