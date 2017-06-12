package Model;

public class Accounts {

    private int accountID;
    private int bankID;
    private int typeID;
    private double amount;
    private String accountNum;
    private String bankName;
    private String typeName;

    // Constructors
    public Accounts() {
    }

    public Accounts(int accountID, int bankID, int typeID, double amount, String accountNum, String bankName, String typeName) {
        this.accountID = accountID;
        this.bankID = bankID;
        this.typeID = typeID;
        this.amount = amount;
        this.accountNum = accountNum;
        this.bankName = bankName;
        this.typeName = typeName;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}