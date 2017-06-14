package Model;

import java.util.Date;

public class Bills {

    private int billId;
    private double billAmount;
    private Date dueDate;
    private int userId;
    private int paid;
    private String comment;
    private String issuerName;
    private Date createdAt;

    public Bills(int billId, double billAmount, Date dueDate, int userId, int paid, String comment, String issuerName, Date createdAt) {
        this.billId = billId;
        this.billAmount = billAmount;
        this.dueDate = dueDate;
        this.userId = userId;
        this.paid = paid;
        this.comment = comment;
        this.issuerName = issuerName;
        this.createdAt = createdAt;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
