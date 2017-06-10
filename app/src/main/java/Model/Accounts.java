package Model;

public class Accounts {

    /*
    Account ID:
    User ID: ??
    Bank ID: 0 - DBS, 1 - Maybank, 2 - OCBC, 3 - Standard Chartered, 4 - HSBC, 5 - UOB
    Account Num: Get from JSON
    Type ID: 0 - Credit Card, 1- Debit Card, 2 - Savings Account, 3 - Current Account, 4 - POSB Everyday Card, 5 - OCBC 365 Card
    Amount: Get from JSON
     */
    //private String[] listOfBanks = new String[] {"DBS", "Maybank", "OCBC", "Standard Chartered", "HSBC", "UOB"};
    //private String[] listOfTypes = new String[]{"Credit Card", "Debit Card", "Savings Account", "Current Account", "POSB Everyday Card", "OCBC 365 Card"};

    private int accountId;
    private String accountNum;
    private double amount;
    private String bankname;
    private String type_name;
    private int bankId;
    private int typeId;

    // Constructors
    public Accounts() {
    }

    public Accounts(int accountId, String accountNum, double amount, String bankname, String type_name, int bankId, int typeId) {
        this.accountId = accountId;
        this.accountNum = accountNum;
        this.amount = amount;
        this.bankname = bankname;
        this.type_name = type_name;
        this.bankId = bankId;
        this.typeId = typeId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

}