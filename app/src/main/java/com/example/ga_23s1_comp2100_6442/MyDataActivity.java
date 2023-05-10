package com.example.ga_23s1_comp2100_6442;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;

import com.example.ga_23s1_comp2100_6442.model.User;

public class MyDataActivity extends Application {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyDataActivity.user = user;
    }
}