package com.breakdown.bilader.adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breakdown.bilader.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageLoadAdapter extends
                              RecyclerView.Adapter< ImageLoadAdapter.ImageHolder > {
    private Context context;
    ArrayList< Uri > uri;

    public ImageLoadAdapter( Context context, ArrayList< Uri > uri ) {
        this.context = context;
        this.uri = uri;
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageAdded;

        public ImageHolder( @NonNull View itemView ) {
            super( itemView );
            imageAdded = itemView.findViewById( R.id.image_upload );
        }
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder( @NonNull ViewGroup parent,
                                           int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_add_image, parent, false );

        return new ImageLoadAdapter.ImageHolder( itemView );
    }

    @Override
    public void onBindViewHolder( @NonNull ImageHolder holder, int position ) {
        holder.imageAdded.setImageURI( uri.get( position ) );
        Picasso.get().load( uri.get( position )).fit().centerInside().into( holder.imageAdded );

    }

    @Override
    public int getItemCount() {
        return uri.size();
    }


}






