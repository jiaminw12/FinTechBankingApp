package Model;

import java.util.Date;

public class Payments {

    private int paymentsId;
    private int userId;
    private int accountId;
    private int billId;
    private Date createdAt;
    private double billAmount;
    private Date dueDate;
    private int paid;
    private String issuerName;
    private String comment;

    public Payments(int paymentsId, int userId, int accountId, int billId, Date createdAt, double billAmount, Date dueDate, int paid, String issuerName, String comment) {
        this.paymentsId = paymentsId;
        this.userId = userId;
        this.accountId = accountId;
        this.billId = billId;
        this.createdAt = createdAt;
        this.billAmount = billAmount;
        this.dueDate = dueDate;
        this.paid = paid;
        this.issuerName = issuerName;
        this.comment = comment;
    }

    public int getPaymentsId() {
        return paymentsId;
    }

    public void setPaymentsId(int paymentsId) {
        this.paymentsId = paymentsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
