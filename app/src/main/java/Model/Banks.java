package Model;

public class Banks {

    private int bankId;
    private String bankname;
    private String bankShortName;
    private int bankCode;

    public Banks(){}

    public Banks(int bankId, String bankname, String bankShortName, int bankCode) {
        this.bankId = bankId;
        this.bankname = bankname;
        this.bankShortName = bankShortName;
        this.bankCode = bankCode;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }


}
