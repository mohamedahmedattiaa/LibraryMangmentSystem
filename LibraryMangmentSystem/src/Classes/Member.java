package Classes;

public class Member {
    private String memberId;
    private String name;
    private String Email;
    private linkedlist booksBorrowed;
    private IDGenerator idGenerator;

    public Member( String name, String Email) {
        if(Email.contains("@") && Email.contains(".")){
            this.Email = Email;
        }
        else throw new IllegalArgumentException("Invalid Email");
        this.memberId = IDGenerator.generateMemberID(name,Email);
        this.name = name;
        this.booksBorrowed = new linkedlist();

    }

    public Member() {
    }

    public String getmemberId() {return memberId;}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return Email;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", Email='" + Email + '\'' +

                '}';
    }
}
