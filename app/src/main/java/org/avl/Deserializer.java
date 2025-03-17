package org.avl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Deserializer {
    AVLTree tree = new AVLTree();

    AVLTree deserialize(String data) {
        Queue<String> nodes = new LinkedList<>();
        nodes.addAll(Arrays.asList(data.split(",")));
        construct_tree(nodes);
        return tree;
    }

    void construct_tree(Queue<String> nodes) {
        while (!nodes.isEmpty()) {
            String data = nodes.poll();
            if (!data.equals("x")) {
                int new_value = Integer.parseInt(data);
                tree.insert(new_value);
            }
        }
    }
}