public class Main2 {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static class SinglyLinkedList {
        Node head;

        // Add token at the beginning
        public void addFirst(int token) {
            Node newNode = new Node(token);
            newNode.next = head;
            head = newNode;
        }

        // Add token at the end
        public void addLast(int token) {
            Node newNode = new Node(token);

            if (head == null) {
                head = newNode;
                return;
            }

            Node current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;
        }

        // Insert token at a given index
        public void insertAt(int index, int token) {
            Node newNode = new Node(token);

            if (index == 0) {
                newNode.next = head;
                head = newNode;
                return;
            }

            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            newNode.next = current.next;
            current.next = newNode;
        }

        // Display the linked list
        public void display() {
            Node current = head;

            while (current != null) {
                System.out.print(current.data + " -> ");
                current = current.next;
            }

            System.out.println("null");
        }
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();

        list.addLast(101);
        list.addLast(102);
        list.addLast(103);

        list.addFirst(100);
        list.addLast(104);
        list.insertAt(2, 150);

        list.display();
    }
}