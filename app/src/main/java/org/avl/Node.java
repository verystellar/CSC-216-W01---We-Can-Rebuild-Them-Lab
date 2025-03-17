package org.avl;

public class Node {
    Node left, right;
    int data;
    int balance_factor;
    int height;

    Node(int data) {
        this.data = data;
    }

    Node get_left() {
        return left;
    }
    Node get_right() {
        return right;
    }
    int get_data() {
        return data;
    }



}
