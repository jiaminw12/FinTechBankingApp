package Model;

import java.util.Date;

public class Schedule {

    private int scheduleId;
    private int userId;
    private int accountId;
    private String issuerName;
    private Date date;
    private double amount;

    public Schedule(){}

    public Schedule(int scheduleId, int userId, int accountId, String issuerName, Date date, double amount) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.accountId = accountId;
        this.issuerName = issuerName;
        this.date = date;
        this.amount = amount;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
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

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
