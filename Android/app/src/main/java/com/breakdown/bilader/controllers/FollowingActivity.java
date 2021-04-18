package com.breakdown.bilader.controllers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.FollowersAdapter;
import com.breakdown.bilader.adapters.FollowersAdapter;
import com.breakdown.bilader.models.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FollowingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<User> followingList;
    private FollowersAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        setSupportActionBar(findViewById(R.id.wishlistToolBar));

        recyclerView = findViewById(R.id.followingRecycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        User user1 = new User("Eren Demirel", "mail@mail.com","avatar_male");
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

        followingList = new ArrayList<>();
        followingList.add(user1);
        followingList.add(user2);
        followingList.add(user3);
        followingList.add(user4);
        followingList.add(user5);
        followingList.add(user6);
        followingList.add(user7);
        followingList.add(user8);
        followingList.add(user9);
        followingList.add(user10);
        followingList.add(user11);
        followingList.add(user12);

        adapter = new FollowersAdapter(this, followingList);
        recyclerView.setAdapter(adapter);

    }
}
