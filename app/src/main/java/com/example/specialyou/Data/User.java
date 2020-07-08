package com.example.specialyou.Data;


import java.util.List;

public class User {
    public String userID;
    public List<String> shoppingList;

    public User(){

    }

    public User(String userName, List<String> shoppingList) {
        this.userID = userName;
        this.shoppingList = shoppingList;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userName) {
        this.userID = userName;
    }

    public List<String> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<String> shoppingList) {
        this.shoppingList = shoppingList;
    }
}


