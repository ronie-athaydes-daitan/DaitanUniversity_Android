package com.daitangroup.daitanuniversityandroid.retrofit.response;

import com.daitangroup.daitanuniversityandroid.model.User;

import java.util.List;

@SuppressWarnings("unused")
public class UsersResponse {

    private List<User> items;

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }
}