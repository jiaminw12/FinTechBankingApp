package Model;

import java.util.Date;

public class Rewards {

    private int rewardsId;
    private int userId;
    private int points;
    private Date createdAt;
    private Date expiryAt;
    private String cardId;


    public Rewards(int rewardsId, int userId, int points, Date createdAt, Date expiryAt, String cardId) {
        this.rewardsId = rewardsId;
        this.userId = userId;
        this.points = points;
        this.createdAt = createdAt;
        this.expiryAt = expiryAt;
        this.cardId = cardId;
    }

    public int getRewardsId() {
        return rewardsId;
    }

    public void setRewardsId(int rewardsId) {
        this.rewardsId = rewardsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(Date expiryAt) {
        this.expiryAt = expiryAt;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

}
