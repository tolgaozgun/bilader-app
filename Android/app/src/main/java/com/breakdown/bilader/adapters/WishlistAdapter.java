
package com.breakdown.bilader.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.breakdown.bilader.fragments.WishlistFragment;
import com.breakdown.bilader.models.*;
import java.util.*;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ProductHolder> {
    private WishlistFragment mmContext;
    private ArrayList<Product> products;

    public WishlistAdapter(WishlistFragment mContext, ArrayList<Product> products) {
        this.mmContext = mContext;
        this.products = products;
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        public ImageView imageProductSeller;
        public ImageView imageProduct;
        public TextView textUserName;
        public TextView textProductName;
        public TextView textProductPrice;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            textProductName = itemView.findViewById(R.id.text_product_name);
            imageProduct = itemView.findViewById(R.id.image_product);
            imageProductSeller = itemView.findViewById(R.id.image_avatar_product_seller);
            textUserName = itemView.findViewById(R.id.text_product_seller_name);
        }
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_products, parent, false);

        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = products.get(position);
        holder.textUserName.setText(product.getSeller().getUserName());
        holder.textProductName.setText(product.getTitle());
        holder.imageProduct.setImageResource(mmContext.getResources().getIdentifier(product.getPicture(),"drawable",mmContext.getActivity().getPackageName()));
        holder.imageProductSeller.setImageResource(mmContext.getResources().getIdentifier(product.getSeller().getUserAvatar(),"drawable",mmContext.getActivity().getPackageName()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
