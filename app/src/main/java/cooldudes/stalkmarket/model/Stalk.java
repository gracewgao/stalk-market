package cooldudes.stalkmarket.model;

public class Stalk {

    private String sId;
    private int template, currentPrice, buyPrice, quantity;

    public Stalk(){}

    public Stalk(String sId, int template, int current, int buy){
        this.sId = sId;
        this.template = template;
        this.currentPrice = current;
        this.buyPrice = buy;
        this.quantity = 0;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}