public class Main2 {

    static class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }

    public static String kthFromEnd(Node head, int k) {
        Node lead = head;
        Node trail = head;

        // Move lead pointer k nodes ahead
        for (int i = 0; i < k; i++) {
            if (lead == null) {
                return "Invalid k";
            }
            lead = lead.next;
        }

        // Move both pointers together
        while (lead != null) {
            lead = lead.next;
            trail = trail.next;
        }

        return trail.data;
    }

    public static void main(String[] args) {

        // Sample 1
        Node head1 = new Node("301");
        head1.next = new Node("302");
        head1.next.next = new Node("303");
        head1.next.next.next = new Node("304");
        head1.next.next.next.next = new Node("305");
        head1.next.next.next.next.next = new Node("306");

        int k1 = 2;
        String result1 = kthFromEnd(head1, k1);

        if (result1.equals("Invalid k")) {
            System.out.println(result1);
        } else {
            System.out.println(k1 + " from the end: " + result1);
        }

        // Sample 2
        Node head2 = new Node("301");
        head2.next = new Node("302");
        head2.next.next = new Node("303");

        int k2 = 5;
        String result2 = kthFromEnd(head2, k2);

        if (result2.equals("Invalid k")) {
            System.out.println(result2);
        } else {
            System.out.println(k2 + " from the end: " + result2);
        }
    }
}