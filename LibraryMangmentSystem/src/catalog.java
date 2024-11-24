public class catalog {
    linkedlist linkedlist = new linkedlist();

    public Boolean add(Book book ,int id) {
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
        book.setAvailablityStatus(statues);
        return book.toString();
     }
     public  Boolean search (int id){
         return linkedlist.search(id);
     }

}