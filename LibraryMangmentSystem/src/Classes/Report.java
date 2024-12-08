package Classes;
import java.util.List;

public class Report {

    public void generateReportForMember(String memberid) { // admin and member can access this
        List loans = PendingRequestsQueue.searchLoans(memberid);
        while (loans.getFirst() != null) {
            loans.getFirst().toString();
            loans.removeFirst();
        }


    }
}
