public class linkedlist {
    private Node head;


    public linkedlist() {
        head = null;
    }

    void insertAtBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    void insertAtEnd(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }

    void insetAtPosition(int data, int pos) {
        if (pos == 0) {
            insertAtBeginning(data);
            return;
        }
        Node newNode = new Node(data);
        Node temp = head;
        for (int i = 0; i < pos - 1 && temp != null; i++) {
            temp = temp.next; // transverse with the values
        }
        if (temp != null) {
            newNode.next = temp.next;
            temp.next = newNode;
        } else {
            System.out.println("Position out of bounds");
        }
    }

    void display() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " "); // transverse the values
            temp = temp.next;
        }
        System.out.println();
    }

    void deleteAtEnd() {
        if (head == null || head.next == null) {
            head = null;
            return;
        }
        Node temp = head;
        while (temp.next.next != null) {
            temp = temp.next;
        }
        temp.next = null;
    }

    public void deleteAtPosition(int pos) {
        if (pos == 0 && head != null) {
            head = head.next;
            return;
        }
        Node temp = head;
        for (int i = 0; i < pos - 1 && temp != null; i++) {
            temp = temp.next; // transverse with the values
        }
        if (temp != null && temp.next != null) {
            temp.next = temp.next.next;
        } else {
            System.out.println("position out of bounds");
        }
    }

    boolean search(int x) {
        Node temp = head;
        while (temp != null) {
            if (temp.data == x) { // we transverse the value with
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    int get(int index) {
        Node temp = head;
        for (int i = 0; i < index && temp != null; i++) {
            temp = temp.next;
        }
        if (temp != null) {
            return temp.data;
        } else {
            throw new IndexOutOfBoundsException("Index out of bounce in this linked list");
        }
    }

    int findMax() {
        if (head == null) {
            throw new IllegalStateException("The list is empty");
        }
        int max = head.data;
        Node temp = head;
        while (temp != null) {
            if (temp.data > max) {
                max = temp.data;
            }
            temp = temp.next;
        }
        return max;
    }

    int findMin() {
        if (head == null) {
            throw new IllegalStateException("The list is empty");
        }
        int min = head.data;
        Node temp = head;
        while (temp != null) {
            if (temp.data < min) {
                min = temp.data;
            }
            temp = temp.next;
        }
        return min;
    }

    int countOfNodesInLinkedlist() {
        Node temp = head;
        int count = 0;
        while (temp != null) {// transverse the values
            temp = temp.next;
            count++;
        }
        return count;
    }
}
