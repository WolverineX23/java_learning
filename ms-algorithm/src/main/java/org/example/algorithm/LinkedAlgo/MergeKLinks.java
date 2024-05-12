package org.example.algorithm.LinkedAlgo;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 合并 K 个升序链表 - PriorityQueue 实现
 * 力扣 23
 *
 */
public class MergeKLinks {

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0)
            return null;

        PriorityQueue<ListNode> queue = new PriorityQueue<>(lists.length, new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });

        ListNode preHead = new ListNode(-1);
        ListNode p = preHead;

        // 添加所有链表的第一个元素
        for (ListNode node : lists) {
            if (node != null)
                queue.offer(node);
        }

        // 开始合并 K 个元素
        while (!queue.isEmpty()) {
            p.next = queue.poll();
            p = p.next;

            if (p.next != null)
                queue.offer(p.next);
        }

        return preHead.next;
    }
}
