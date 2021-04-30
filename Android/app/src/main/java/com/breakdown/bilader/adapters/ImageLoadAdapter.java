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

    /**
     * A constructor that holds properties of fragment adapter
     *
     * @param context is the location of the current fragment and its internal
     *                elements and methods
     * @param uri     list of the image url's
     */
    public ImageLoadAdapter( Context context, ArrayList< Uri > uri ) {
        this.context = context;
        this.uri = uri;
    }

    /**
     * A class that holds id's of elements in layout
     */
    public class ImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageAdded;

        /**
         * A constructor that holds id's of views
         *
         * @param itemView is the references of an item
         */
        public ImageHolder( @NonNull View itemView ) {
            super( itemView );
            imageAdded = itemView.findViewById( R.id.image_upload );
        }
    }

    /**
     * a method that creates new card view elements
     *
     * @param parent   is the The ViewGroup into which the new View will be
     *                 added after it is bound to an adapter position.
     * @param viewType The view type of the new View
     * @return a new ViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public ImageHolder onCreateViewHolder( @NonNull ViewGroup parent,
                                           int viewType ) {
        View itemView;

        itemView =
                LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_add_image, parent, false );

        return new ImageLoadAdapter.ImageHolder( itemView );
    }

    /**
     * a method called by RecyclerView to display the data at the specified
     * position
     *
     * @param holder   is the ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position is The position of the item within the adapter's data
     *                 set.
     */
    @Override
    public void onBindViewHolder( @NonNull ImageHolder holder, int position ) {
        holder.imageAdded.setImageURI( uri.get( position ) );
        Picasso.get().load( uri.get( position ) ).fit().centerInside().into( holder.imageAdded );

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return uri.size();
    }

}






