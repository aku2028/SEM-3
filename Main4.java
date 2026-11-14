public class Main4 {

    static class Node {
        String name;
        Node prev;
        Node next;

        Node(String name) {
            this.name = name;
            this.prev = null;
            this.next = null;
        }
    }

    static class DoublyLinkedList {
        Node head;
        Node tail;

        // Add coach at the end
        public void addLast(String name) {
            Node newNode = new Node(name);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
        }

        // Remove coach by name
        public void remove(String name) {
            Node current = head;

            while (current != null && !current.name.equals(name)) {
                current = current.next;
            }

            if (current == null) {
                return;
            }

            if (current == head) {
                head = current.next;
            } else {
                current.prev.next = current.next;
            }

            if (current == tail) {
                tail = current.prev;
            } else {
                current.next.prev = current.prev;
            }
        }

        // Print from engine end to guard end
        public void printForward() {
            Node current = head;

            System.out.print("Forward: ");

            while (current != null) {
                System.out.print(current.name);
                if (current.next != null) {
                    System.out.print(" <-> ");
                }
                current = current.next;
            }

            System.out.println();
        }

        // Print from guard end to engine end
        public void printBackward() {
            Node current = tail;

            System.out.print("Backward: ");

            while (current != null) {
                System.out.print(current.name);
                if (current.prev != null) {
                    System.out.print(" <-> ");
                }
                current = current.prev;
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        DoublyLinkedList train = new DoublyLinkedList();

        train.addLast("Engine");
        train.addLast("A1");
        train.addLast("B1");
        train.addLast("B2");
        train.addLast("Guard");

        train.remove("B1");

        train.printForward();
        train.printBackward();
    }
}