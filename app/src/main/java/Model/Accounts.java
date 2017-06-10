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
    private String title, genre, year;
    private int accountID, bankID, typeID, amount;
    private String accountNum;
    private String[] listOfBanks = new String[] {"DBS", "Maybank", "OCBC", "Standard Chartered", "HSBC", "UOB"};
    private String[] listOfTypes = new String[] {"Credit Card", "Debit Card", "Savings Account", "Current Account", "POSB Everyday Card", "OCBC 365 Card"};

    // Constructors
    public Accounts() {
    }

    public Accounts(int accountID, int bankID, String accountNum, int typeID, int amount) {
        this.accountID = accountID;
        this.bankID = bankID;
        this.accountNum = accountNum;
        this.typeID = typeID;
        this.amount = amount;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getBankID() {
        return listOfBanks[bankID];
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getTypeID() {
        return listOfTypes[typeID];
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
