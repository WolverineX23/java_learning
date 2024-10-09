package org.example.algorithm.LinkedAlgo;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LinkTest {
    public static ListNode mergeKLinks(ListNode[] links) {
        if (links == null || links.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));

        // 添加首个 节点
        for (ListNode node : links) {
            queue.offer(node);
        }

        ListNode preHead = new ListNode(-1);
        ListNode p = preHead;

        while (!queue.isEmpty()) {
            p.next = queue.poll();
            p = p.next;

            if (p.next != null) {
                queue.offer(p.next);
            }
        }

        return preHead.next;
    }
}
