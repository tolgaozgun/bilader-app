package com.breakdown.bilader.controllers;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.FollowersAdapter;
import com.breakdown.bilader.models.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FollowersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<User> followerList;
    private FollowersAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        recyclerView = findViewById(R.id.followersRecycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        User user1 = new User("Yahya Demirel", "mail@mail.com","avatar_male");
        User user2 = new User("Burcu Kaplan", "mail@mail.com","avatar_female");
        User user3 = new User("Korhan Kaya", "mail@mail.com","avatar_male");
        User user4 = new User("Deniz Gökçen", "mail@mail.com","avatar_female");
        User user5 = new User("Tolga Özgün", "mail@mail.com","avatar_male");
        User user6 = new User("Burak Yıldır", "mail@mail.com","avatar_male");
        User user7 = new User("Mansur Yavaş", "mail@mail.com","avatar_male");
        User user8 = new User("Okan Tekman", "mail@mail.com","avatar_no_gender");
        User user9 = new User("Salim Çıracı", "mail@mail.com","avatar_male");
        User user10 = new User("Mustafa Nakeeb", "mail@mail.com","avatar_male");
        User user11 = new User("Kenan Demir", "mail@mail.com","avatar_no_gender");
        User user12 = new User("Berşan Özgür", "mail@mail.com","avatar_no_gender");

        followerList = new ArrayList<>();
        followerList.add(user1);
        followerList.add(user2);
        followerList.add(user3);
        followerList.add(user4);
        followerList.add(user5);
        followerList.add(user6);
        followerList.add(user7);
        followerList.add(user8);
        followerList.add(user9);
        followerList.add(user10);
        followerList.add(user11);
        followerList.add(user12);

        adapter = new FollowersAdapter(this, followerList);
        recyclerView.setAdapter(adapter);

    }
}
