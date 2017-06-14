package Model;

public class Transactions {

    private int transactionId;
    private double transactionAmount;
    private String trasactionDetail;
    private int payeeId;
    private int categoryId;

    public Transactions(int transactionId, double transactionAmount, String trasactionDetail, int payeeId, int categoryId) {
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.trasactionDetail = trasactionDetail;
        this.payeeId = payeeId;
        this.categoryId = categoryId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTrasactionDetail() {
        return trasactionDetail;
    }

    public void setTrasactionDetail(String trasactionDetail) {
        this.trasactionDetail = trasactionDetail;
    }

    public int getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(int payeeId) {
        this.payeeId = payeeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
