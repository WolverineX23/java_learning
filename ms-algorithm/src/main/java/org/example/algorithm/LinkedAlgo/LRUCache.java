package org.example.algorithm.LinkedAlgo;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个满足 最近最少使用缓存约束 的数据结构
 * 当缓存满了，逐出 最久未使用的关键字
 */
public class LRUCache {

    class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node() {}

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    Node head, tail;    // 靠近 tail 的节点，最近使用

    // hash 表
    Map<Integer, Node> map;
    int capacity, size;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;

        map = new HashMap<>(capacity);

        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        } else {
            // 更新时间序
            Node node = map.get(key);
            dealExistNode(node);

            return map.get(key).value;
        }
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            dealExistNode(node);
        } else {
            // 若缓存满了
            if(capacity == size) {
                // 移除最久未使用的节点 - head.next
                Node oldestNode = head.next;
                head.next = oldestNode.next;
                oldestNode.next.prev = head;
                map.remove(oldestNode.key);
                size--;
            }

            // 将新节点加到末尾
            Node newNode = new Node(key, value);
            dealNewNode(newNode);
            map.put(key, newNode);
            size++;
        }
    }

    // 处理原链表中已存在的节点，将其放到链表末尾
    private void dealExistNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;

        // 将当前节点放到链表尾部
        dealNewNode(node);
    }

    // 将最新操作的节点放到链表尾部
    private void dealNewNode(Node newNode) {
        Node lastNode = tail.prev;
        lastNode.next = newNode;
        newNode.prev = lastNode;

        newNode.next = tail;
        tail.prev = newNode;
    }
}
