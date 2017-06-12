package Model;

public class Payees {

    private int payeeId;
    private String payeeName;
    private String payeeAcctNum;

    public Payees(){}

    public Payees(int payeeId, String payeeName, String payeeAcctNum) {
        this.payeeId = payeeId;
        this.payeeName = payeeName;
        this.payeeAcctNum = payeeAcctNum;
    }

    public int getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(int payeeId) {
        this.payeeId = payeeId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeAcctNum() {
        return payeeAcctNum;
    }

    public void setPayeeAcctNum(String payeeAcctNum) {
        this.payeeAcctNum = payeeAcctNum;
    }



}



