package Classes;

public class Node {
     Book book;
     Node next;

    public Node(Book book) {
        this.book = book;
        this.next = null;
    }

    public Book getBook() {
        return book;
    }

    public Node getNext() {
        return next;
    }
}
