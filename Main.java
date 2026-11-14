public class Main {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public static Node partition(Node head, int passMark) {
        Node belowHead = null;
        Node belowTail = null;

        Node aboveHead = null;
        Node aboveTail = null;

        Node current = head;

        while (current != null) {
            Node nextNode = current.next;
            current.next = null;

            if (current.data < passMark) {
                if (belowHead == null) {
                    belowHead = current;
                    belowTail = current;
                } else {
                    belowTail.next = current;
                    belowTail = current;
                }
            } else {
                if (aboveHead == null) {
                    aboveHead = current;
                    aboveTail = current;
                } else {
                    aboveTail.next = current;
                    aboveTail = current;
                }
            }

            current = nextNode;
        }

        // Join both chains
        if (belowHead == null) {
            return aboveHead;
        }

        belowTail.next = aboveHead;

        return belowHead;
    }

    public static void display(Node head) {
        Node current = head;

        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }

        System.out.println("null");
    }

    public static void main(String[] args) {

        Node head = new Node(55);
        head.next = new Node(32);
        head.next.next = new Node(78);
        head.next.next.next = new Node(20);
        head.next.next.next.next = new Node(40);
        head.next.next.next.next.next = new Node(35);

        int passMark = 40;

        head = partition(head, passMark);

        display(head);
    }
}