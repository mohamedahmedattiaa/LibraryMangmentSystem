public class linkedlist {
    private Node head;


    public linkedlist() {
        head = null;
    }

    void insertAtBeginning(Book book) {
        Node newNode = new Node(book);
        newNode.next = head;
        head = newNode;
    }

    void insertAtEnd(Book book) {
        Node newNode = new Node(book);
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

    void insetAtPosition(Book book, int pos) {
        if (pos == 0) {
            insertAtBeginning(book);
            return;
        }
        Node newNode = new Node(book);
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
            System.out.print(temp.book + " "); // transverse the values
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

    boolean search(String id) {
        Node temp = head;
        while (temp != null) {
            if (temp.book.getBookID() == id) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    Book get(int index) { // Check id
        Node temp = head;
        for (int i = 0; i < index && temp != null; i++) {
            temp = temp.next;
        }
        if (temp != null) {
            return temp.book;
        } else {
            throw new IndexOutOfBoundsException("Index out of bounce in this linked list");
        }
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
