package org.example.algorithm.LinkedAlgo;

/**
 * 反转链表
 * 力扣: 206
 *
 */
public class ReverseLink {

    // 递归
    public ListNode reverseList(ListNode head) {

        if (head == null || head.next == null)
            return head;

        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;

        return newHead;
    }

    // 迭代
    public ListNode reverseList2(ListNode head) {

        ListNode prev = null, curr = head;

        while (curr != null) {

            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }
}
