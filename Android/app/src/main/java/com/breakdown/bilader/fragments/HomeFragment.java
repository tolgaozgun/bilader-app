package com.breakdown.bilader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.adapters.ProductAdapter;
import com.breakdown.bilader.models.Product;
import com.breakdown.bilader.models.User;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Product> productList;
    private ProductAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.biltraderRecycler);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        User user1 = new User("Yahya Demirel", "mail@mail.com","avatar_male","12");
        User user2 = new User("Burcu Kaplan", "mail@mail.com","avatar_female", "12");
        User user3 = new User("Korhan Kaya", "mail@mail.com","avatar_male", "12");
        User user4 = new User("Deniz Gökçen", "mail@mail.com","avatar_female", "12");
        User user5 = new User("Tolga Özgün", "mail@mail.com","avatar_male", "12");

        Product product1 = new Product("product_sample", "The Epic of Gilgamesh","demo1",120, user1);
        Product product2 = new Product("product_sample2", "brand new dress","demo1",120, user2);
        Product product3 = new Product("product_sample3", "basys-3","demo1",120, user3);
        Product product4 = new Product("product_sample", "random","demo1",120, user4);
        Product product5 = new Product("product_sample", "random","demo1",120, user5);



        productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);



        adapter = new ProductAdapter(this,productList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}