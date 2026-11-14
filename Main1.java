public class Main1 {

    static class Node {
        String name;
        Node prev, next;

        Node(String name) {
            this.name = name;
            this.prev = null;
            this.next = null;
        }
    }

    static class Browser {
        Node head, tail, cur;

        // Open a new tab just after the current tab
        public void open(String name) {
            Node newNode = new Node(name);

            if (cur == null) {
                head = tail = cur = newNode;
                return;
            }

            newNode.prev = cur;
            newNode.next = cur.next;

            if (cur.next != null) {
                cur.next.prev = newNode;
            } else {
                tail = newNode;
            }

            cur.next = newNode;
            cur = newNode;
        }

        // Close the current tab
        public void close() {
            if (cur == null) {
                return;
            }

            Node toRemove = cur;

            if (toRemove.next != null) {
                cur = toRemove.next;
            } else {
                cur = toRemove.prev;
            }

            if (toRemove.prev != null) {
                toRemove.prev.next = toRemove.next;
            } else {
                head = toRemove.next;
            }

            if (toRemove.next != null) {
                toRemove.next.prev = toRemove.prev;
            } else {
                tail = toRemove.prev;
            }

            toRemove.prev = null;
            toRemove.next = null;
        }

        // Move to previous tab
        public void back() {
            if (cur == null || cur.prev == null) {
                System.out.println("BACK: already at first tab");
                return;
            }

            cur = cur.prev;
            System.out.print("BACK: ");
            print();
        }

        // Move to next tab
        public void forward() {
            if (cur == null || cur.next == null) {
                System.out.println("FORWARD: already at last tab");
                return;
            }

            cur = cur.next;
            System.out.print("FORWARD: ");
            print();
        }

        // Print tabs with current tab in brackets
        public void print() {
            Node temp = head;

            while (temp != null) {
                if (temp == cur) {
                    System.out.print("[" + temp.name + "]");
                } else {
                    System.out.print(temp.name);
                }

                if (temp.next != null) {
                    System.out.print(" <-> ");
                }

                temp = temp.next;
            }

            System.out.println();
        }

        // Print tabs from last to first
        public void printBackward() {
            Node temp = tail;

            System.out.print("Backward: ");

            while (temp != null) {
                if (temp == cur) {
                    System.out.print("[" + temp.name + "]");
                } else {
                    System.out.print(temp.name);
                }

                if (temp.prev != null) {
                    System.out.print(" <-> ");
                }

                temp = temp.prev;
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        Browser browser = new Browser();

        browser.open("Mail");
        System.out.print("OPEN Mail: ");
        browser.print();

        browser.open("Docs");
        System.out.print("OPEN Docs: ");
        browser.print();

        browser.open("Maps");
        System.out.print("OPEN Maps: ");
        browser.print();

        browser.back();
        browser.back();
        browser.back();

        browser.open("Drive");
        System.out.print("OPEN Drive: ");
        browser.print();

        browser.close();
        System.out.print("CLOSE Drive: ");
        browser.print();

        browser.forward();

        browser.close();
        System.out.print("CLOSE Maps: ");
        browser.print();

        browser.printBackward();
    }
}