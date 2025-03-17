package org.avl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class AVLTree {
    Node root;
    private int node_count;
    int height;

    int get_height() {
        if (root == null) {
            return 0;
        }
        return root.height;
    }

    int get_node_count() {
        return node_count;
    }

    boolean is_empty() {
        return root == null;
    }

    boolean contains(int key) {
        return contains(root, key);
    }

    boolean contains(Node node, int key) {
        if (node == null) {
            return false;
        }
        int compare = Integer.compare(key, node.data);
        if (compare < 0) {
            return contains(node.left, key);
        } else if (compare > 0) {
            return contains(node.right, key);
        }
        return true;
    }

    boolean insert(int key) {
        if (!contains(root, key)) {
            root = insert(root, key);
            node_count++;
            return true;
        }
        return false;
    }

    Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }
        int compare = Integer.compare(key, node.data); //gives - value if key is less, + if key is more

        if (compare < 0) {
            node.left = insert(node.left, key);
        } else if (compare > 0) {
            node.right = insert(node.right, key);
        }
        update(node);
        return balance(node);
    }

    void update(Node node) {
        int left_height = (node.left == null) ? -1 : node.left.height;
        int right_height = (node.right == null) ? -1 : node.right.height;
        int max_height = Math.max(left_height, right_height) + 1;
        node.height = max_height;
        node.balance_factor = right_height - left_height;
    }

    Node balance(Node node) {
        if (node.balance_factor == -2) { //left heavy

            if (node.left.balance_factor <= 0) { //adding key to left of left node
                return left_left_case(node);
            } else { //we are adding key to right of left node
                return left_right_case(node);
            }

            // Right heavy subtree needs balancing.
        } else if (node.balance_factor == 2) {

            if (node.right.balance_factor >= 0) {
                return right_right_case(node); //adding key to right of right node
            } else {
                return right_left_case(node);
            }
        }
        return node;
    }

    Node left_left_case(Node node) {
        return right_rotation(node);
    }

    Node right_right_case(Node node) {
        return left_rotation(node);
    }

    Node left_right_case(Node node) {
        node.left = left_rotation(node.left);
        return left_left_case(node);
    }

    Node right_left_case(Node node) {
        node.right = right_rotation(node.right);
        return right_right_case(node);
    }

    Node right_rotation(Node node) {
        Node parent = node.left;
        node.left = parent.right;
        parent.right = node;
        update(node);
        update(parent);
        return parent;
    }

    Node left_rotation(Node node) {
        Node parent = node.right;
        node.right = parent.left;
        parent.left = node;
        update(node);
        update(parent);
        return parent;
    }

    boolean remove(int key) {
        if (contains(root, key)) {
            root = remove(root, key);
            node_count--;
            return true;
        }

        return false;
    }

    Node remove(Node node, int key) {
        if (node == null) return null;
        int compare = Integer.compare(key, node.data);

        //go left
        if (compare < 0) {
            node.left = remove(node.left, key);

            //go right
        } else if (compare > 0) {
            node.right = remove(node.right, key);

            // aka theyre the same, found it
        } else {

            // This is the case with only a right subtree or no subtree at all.
            // In this situation just swap the node we wish to remove
            // with its right child.

            //aka skip the value
            if (node.left == null) {
                return node.right;

                // This is the case with only a left subtree or
                // no subtree at all. In this situation just
                // swap the node we wish to remove with its left child.

                //aka skip the value
            } else if (node.right == null) {
                return node.left;

                // When removing a node from a binary tree with two links the
                // successor of the node being removed can either be the largest
                // value in the left subtree or the smallest value in the right
                // subtree. As a heuristic, I will remove from the subtree with
                // the greatest height in hopes that this may help with balancing.

                //in other words, set the empty space to either the biggest left or
                //smallest right, remove from the bigger of the trees if possible
            } else {

                // Choose to remove from left subtree
                if (node.left.height > node.right.height) {

                    // Swap the value of the successor into the node.
                    int successor_value = find_max(node.left);
                    node.data = successor_value;

                    // Find the largest node in the left subtree.
                    node.left = remove(node.left, successor_value);

                } else {

                    // Swap the value of the successor into the node.
                    int successor_value = find_min(node.right);
                    node.data = successor_value;

                    // Go into the right subtree and remove the leftmost node we
                    // found and swapped data with. This prevents us from having
                    // two nodes in our tree with the same value.
                    node.right = remove(node.right, successor_value);
                }
            }
        }
        update(node);
        return balance(node);
    }

    //go far left, smallest
    int find_min(Node node) {
        while (node.left != null) node = node.left;
        return node.data;
    }

    //go far right, biggest
    int find_max(Node node) {
        while (node.right != null) node = node.right;
        return node.data;
    }

    String serialize() {
        return serialize(root);
    }

    String serialize(Node node) {
        if (node == null) return "x"; //empty

        String left = serialize(node.left);
        String right = serialize(node.right);

        return node.data + "," + left + "," + right;
    }

}

