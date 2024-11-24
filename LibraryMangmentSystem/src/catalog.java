public class catalog {
    linkedlist linkedlist = new linkedlist();

    public Boolean add(Book book) {
        if (book == null) {
            System.out.println("error");
            return false;
        } else if (linkedlist.search(book)) {
            System.out.println("Book already exists");
            return false;
        } else {
            linkedlist.insertAtBeginning(book);
            return true;
        }
     }

}