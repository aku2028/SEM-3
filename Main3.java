public class Main3 {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public static Node merge(Node first, Node second) {
        Node dummy = new Node(0);
        Node tail = dummy;

        while (first != null && second != null) {
            if (first.data <= second.data) {
                tail.next = first;
                first = first.next;
            } else {
                tail.next = second;
                second = second.next;
            }

            tail = tail.next;
        }

        // Attach the remaining nodes
        if (first != null) {
            tail.next = first;
        } else {
            tail.next = second;
        }

        return dummy.next;
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

        // First sorted waitlist
        Node first = new Node(3);
        first.next = new Node(8);
        first.next.next = new Node(15);

        // Second sorted waitlist
        Node second = new Node(5);
        second.next = new Node(8);
        second.next.next = new Node(20);
        second.next.next.next = new Node(25);

        Node result = merge(first, second);

        System.out.println("Merged Waitlist:");
        display(result);
    }
}