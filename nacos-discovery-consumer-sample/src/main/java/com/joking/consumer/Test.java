package com.joking.consumer;

/**
 * 两个有序链表的合并
 *
 * @author <a href="mailto:jokinglove@foxmail.com">JOKING</a>
 * @since
 */
public class Test {
    public static void main(String[] args) {
        Node node0 = Node.val(0);
        Node node1 = Node.val(1);
        Node node2 = Node.val(2);
        Node node3 = Node.val(3);
        Node node4 = Node.val(4);
        Node node5 = Node.val(5);

        node0.next = node1;
        node1.next = node4;

        node2.next = node3;
        node3.next = node5;

        System.out.println("head1 = " + node0);
        System.out.println("head2 = " + node2);

        Node node = merge1(node0, node2);
        System.out.println("result = " + node);
    }

    private static Node merge1(Node head1, Node head2) {
        if(head1 == null && head2 == null) {
            return null;
        }
        if(head1 == null) return head2;
        if(head2 == null) return head1;

        Node head = null;
        if(head1.value < head2.value) {
            head = head1;
            head.next = merge1(head1.next, head2);
        } else {
            head = head2;
            head.next = merge1(head1, head2.next);
        }
        return head;
    }

    private static Node merge(Node first, Node second) {
        Node result = null;
        Node fCurr = first;
        Node sCurr = second;
        while(fCurr.next != null) {
            int fValue = fCurr.value;
            while(sCurr.next != null) {
                int sValue = sCurr.value;
                if(fValue > sValue) {
                    if(result == null) {
                        result = sCurr;
                    } else {
                        result.next = sCurr;
                    }
                    sCurr = sCurr.next;
                } else {
                    if(result == null) {
                        result = fCurr;
                    } else {
                        result.next = fCurr;
                    }
                    fCurr = fCurr.next;
                    break;
                }
            }
        }

        return result;
    }


    static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public static Node val(int i) {
            return new Node(i);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + next +
                    '}';
        }
    }
}
