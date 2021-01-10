package cooldudes.stalkmarket.model;

import java.util.ArrayList;

public class Farmer {

    private String uId, name;
    private ArrayList<Stalk> inventory;
    private int balance;

    public Farmer(){}

    public Farmer(String userId, String userName){
        this.uId = userId;
        this.name = userName;
        this.balance = 300;
        this.inventory = new ArrayList<>();
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<Stalk> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Stalk> inventory) {
        this.inventory = inventory;
    }
}
