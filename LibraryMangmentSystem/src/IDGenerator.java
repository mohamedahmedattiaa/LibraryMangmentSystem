public class IDGenerator {
    private static int bookCounter = 100;
    private static int memberCounter = 200;
    private static int loanCounter = 300;

    public static String generateBookID() {
        return "B" + (bookCounter++);
    }

    public static String generateMemberID() {
        return "M" + (memberCounter++);
    }

    public static String generateLoanID() {
        return "L" + (loanCounter++);
    }
}