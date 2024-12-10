package Classes;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Catalog catalog = new Catalog();
        PendingRequestsQueue pendingRequestsQueue = new PendingRequestsQueue();

// Add books to the catalog
        Book book1 = new Book("c", "George washonton", "Fiction");
        Book book2 = new Book("d", "Ibraham lincolin", "Dystopian");
        Book book3 = new Book("a", "Wealm sheckspear", "Dystopian");
        Book book4 = new Book("e", "Albert fred", "Fiction");


        catalog.addBook(book1);
        catalog.addBook(book2);
        catalog.addBook(book3);
        catalog.addBook(book4);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
// Borrow and return books
        Member member1 = new Member("atta", "ezz@gmail.com");
        Member member2 = new Member("ezz", "Mohamed1122@gmail.com");
        Member member3 = new Member("a7med", "Ezz@gmail.com");
        dataBaseMembers.add(member1);
        dataBaseMembers.add(member2);
        dataBaseMembers.add(member3);
//        System.out.println(dataBaseMembers.display());
        Loan.borrowBook("M202", "B100", catalog); // what if he borrowed the same book twice??
        Loan.borrowBook("M201", "B102", catalog); // SHOULD I GET THE OVERDUE DATE FOR THE RETURNED BOOKS ALSO ?
        Loan.borrowBook("M200", "B101", catalog);
        Loan.borrowBook("M202" ,"B100" ,catalog);
//         pendingRequestsQueue.display();
//         System.out.println("//////////////////////////////////////////////");
//         pendingRequestsQueue.display();
//         Report report =new Report();
//         report.generateGeneralReport(catalog);
//         report.generateReportForMember("M200",catalog);
//         Loan.activeLoans.peek().setReturnDate();
//        System.out.println(Loan.activeLoans.peek().getReturnDate());
//        System.out.println("/////////////////////////////////////////////////");
//        System.out.println(Loan.activeLoans.toString());
//
//        Report.displayOverdueBooks(catalog);
        System.out.println("/////////////////////////////////////////");
        Loan.sortloanByMemberID();
        Report report = new Report();
//        Catalog.Sorting("title");
//        Catalog.displayCatalog();
//        report.displayActiveLoans(catalog);
        report.displayPopularGenre(catalog);
    }

}

