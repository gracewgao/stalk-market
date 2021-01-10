package cooldudes.stalkmarket.model;

public class StalkTemplate {

    private int tId;
    private String nickname, realStalk, imgUrl;
    private long lastUpdate;
    private float price;

    public StalkTemplate(){}

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getRealStalk() {
        return realStalk;
    }

    public void setRealStalk(String realStalk) {
        this.realStalk = realStalk;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
