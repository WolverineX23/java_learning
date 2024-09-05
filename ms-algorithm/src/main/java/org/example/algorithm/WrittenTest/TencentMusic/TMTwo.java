package org.example.algorithm.WrittenTest.TencentMusic;

public class TMTwo {

    public static class ListNode {
        int val;
        ListNode next = null;
        public ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * input1: {1,2,2,3,4}, {1,2,1,2,3,3,4}
     * input2: {1,2,3,4,5}, {1,2,4,5}
     *
     * 50%
     *
     * @param a
     * @param b
     * @return
     */
    public ListNode mergeList (ListNode a, ListNode b) {

        ListNode head = a;

        // 先确定 a 和 b 的长度
        int aLen = 0;
        for (ListNode node = a; node != null; node = node.next) {
            aLen++;
        }
        int bLen = 0;
        for (ListNode node = b; node != null; node = node.next) {
            bLen++;
        }

        // 先找到第一个交汇节点
        ListNode firstNode = a;
        while (a.val == b.val) {        // 测试过 a != b
            firstNode = a;
            a = a.next;
            b = b.next;
        }

        // 若 a 比 b 长， 则 a 先走 a-b 步
        if (aLen > bLen) {
            for (int i = 0; i < aLen - bLen; i++) {
                a = a.next;
            }
        } else if (aLen < bLen) {
            for (int i = 0; i < bLen - aLen; i++) {
                b = b.next;
            }
        }

        // 找到第二个交汇点
        ListNode secondNode = a;
        while (a.val != b.val) {
            a = a.next;
            b = b.next;
        }
        secondNode = a;

        // 连接
        firstNode.next = secondNode;
        return head;
    }
}
