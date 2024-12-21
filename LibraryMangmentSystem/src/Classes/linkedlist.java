package Classes;
public class linkedlist {
    private Node head;


    public linkedlist() {
        head = null;
    }

    public Node getHead() {
        return head;
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
            System.out.print(temp.book + " ");
            System.out.println();// transverse the values
            temp = temp.next;
        }
        System.out.println();
    }

    public void delete(String bookId) {
        if (head == null) return;

        if (head.book.getBookID().equals(bookId)) {
            head = head.next;
            return;
        }

        Node current = head;
        while (current.next != null && !current.next.book.getBookID().equals(bookId)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }
    public boolean deleteById(String bookId) {
        Node current = head;
        Node previous = null;

        while (current != null) {
            // Assuming each Node contains a Book object and Book has a getBookID() method
            if (current.book.getBookID().equals(bookId)) {
                if (previous == null) { // Deleting the head node
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                return true; // Book found and deleted
            }
            previous = current;
            current = current.next;
        }
        return false; // Book not found
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

    Book Searchbook(String id) {
        Node temp = head;
        while (temp != null) {
            if (temp.book.getBookID().equals(id)) {
                return temp.book;
            }
            temp = temp.next;
        }
        return null;
    }
    String Findbook(String bookTitle) {
        Node temp = head;
        while (temp != null) {
            if (temp.book.getBookTitle().equals(bookTitle)) {
                return temp.book.getBookID();
            }
            temp = temp.next;
        }
        return null;
    }

    public boolean Search(String id) {
        Node temp = head;
        while (temp != null) {
            if (temp.book.getBookID().equals(id)) {
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
    public void sorting(String Sortedby){
        head = mergeSort(head,Sortedby);
    }

    public Node mergeSort(Node head,String SortedBy) {
        if(head == null || head.next == null) return head;
        Node middle = getMiddle(head);
        Node next =middle.next;
        middle.next = null;

        Node left = mergeSort(head,SortedBy);
        Node right = mergeSort(next,SortedBy);
        return merge(left,right,SortedBy);
    }

    private Node getMiddle(Node head) {
        if (head == null) return head;

        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public  Node merge(Node left,Node right ,String SortedBy) {
        if(left == null ) return right;
        if(right == null ) return left;
        boolean check = false;
        switch (SortedBy) {
            case "title":
            case "Title":
                check = left.book.getBookTitle().compareTo(right.book.getBookTitle()) <= 0;
                break;
            case "Author":
            case "author":
                check = left.book.getAuthor().compareTo(right.book.getAuthor()) <= 0;
                break;
        }
        if (check) {
            left.next = merge(left.next, right, SortedBy);
            return left;
        } else {
            right.next = merge(left, right.next, SortedBy);
            return right;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public String toStringForMember() {
        StringBuilder result = new StringBuilder();
        Node current = this.getHead(); // Assuming getHead returns the head of the list
        while (current != null) {
            result.append(current.book.getBookID()).append(", "); // Replace with desired book details
            current = current.next;
        }
        return result.length() > 0 ? result.substring(0, result.length() - 2) : "No books borrowed";
    }


}