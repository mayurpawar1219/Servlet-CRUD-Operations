package in.coderarmy.model;

public class User {
    private Integer id;
    private String name;
    private long mobileNo;
    private String emailid;

    public User(Integer id, String emailid, long mobileNo, String name) {
        this.id = id;
        this.emailid = emailid;
        this.mobileNo = mobileNo;
        this.name = name;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }
}
