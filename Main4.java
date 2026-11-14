public class Main4 {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public static int middle(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow.data;
    }

    public static void main(String[] args) {

        // Sample 1
        Node head1 = new Node(11);
        head1.next = new Node(22);
        head1.next.next = new Node(33);
        head1.next.next.next = new Node(44);
        head1.next.next.next.next = new Node(55);

        System.out.println("Middle: " + middle(head1));

        // Sample 2
        Node head2 = new Node(11);
        head2.next = new Node(22);
        head2.next.next = new Node(33);
        head2.next.next.next = new Node(44);
        head2.next.next.next.next = new Node(55);
        head2.next.next.next.next.next = new Node(66);

        System.out.println("Middle: " + middle(head2));
    }
}