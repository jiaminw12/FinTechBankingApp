package Model;

public class Cards {
    private int activate;
    private int accountID;
    private int cardID;
    private int cardTypeID;
    private String cardNum;
    private String cardName;
    private String ccv;
    private String cardExpiryDate;

    public Cards(){}

    public Cards(int activate, int accountID, int cardID,  int cardTypeID, String cardNum, String cardName, String ccv, String cardExpiryDate) {
        this.activate = activate;
        this.accountID = accountID;
        this.cardID = cardID;
        this.cardTypeID = cardTypeID;
        this.cardNum = cardNum;
        this.cardName = cardName;
        this.ccv = ccv;
        this.cardExpiryDate = cardExpiryDate;
    }

    public int getActivate() {
        return activate;
    }

    public void setActivate(int activate) {
        this.activate = activate;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getCardsID() {
        return cardID;
    }

    public void setCardsID(int cardID) {
        this.cardID = cardID;
    }

    public int getCardTypeID() {
        return cardTypeID;
    }

    public void setCardTypeID(int cardTypeID) {
        this.cardTypeID = cardTypeID;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }
}