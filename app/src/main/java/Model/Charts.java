package Model;

public class Charts {

    private int color;
    private String category;
    private String amount;

    public Charts(String category, String amount) {
        this.category = category;
        this.amount = amount;
    }

    public Charts(int color, String category, String amount) {
        this.color = color;
        this.category = category;
        this.amount = amount;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
