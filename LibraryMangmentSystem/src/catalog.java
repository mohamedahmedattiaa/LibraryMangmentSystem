public class catalog {
    linkedlist linkedlist = new linkedlist();

    public Boolean add(Book book ,String id) {
        if (book == null) {
            System.out.println("error");
            return false;
        } else if (linkedlist.search(id)) {
            System.out.println("Book already exists");
            return false;
        } else {
            linkedlist.insertAtBeginning(book);
            return true;
        }
     }
     public String updateBook(Book book ,Boolean statues){
        String id = book.getBookID();
        book.setAvailablityStatus(statues);
        return book.toString();
        // correct it and change get in linkedlist
     }
     public  Boolean search (String id){
         return linkedlist.search(id);
     }

}