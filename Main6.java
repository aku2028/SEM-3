public class Main6 {

    static class Node {
        String name;
        Node next;

        Node(String name) {
            this.name = name;
            this.next = null;
        }
    }

    static class CircularList {
        Node tail;

        // Add a player at the end
        public void addLast(String name) {
            Node newNode = new Node(name);

            if (tail == null) {
                tail = newNode;
                tail.next = tail;
            } else {
                newNode.next = tail.next;
                tail.next = newNode;
                tail = newNode;
            }
        }

        // Print players for the given number of turns
        public void printTurns(int turns) {
            if (tail == null) {
                return;
            }

            Node current = tail.next;

            for (int i = 0; i < turns; i++) {
                System.out.print(current.name + " ");
                current = current.next;
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        CircularList players = new CircularList();

        players.addLast("Asha");
        players.addLast("Ravi");
        players.addLast("Neha");

        players.printTurns(7);
    }
}