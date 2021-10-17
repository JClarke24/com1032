package com.example.photoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
Adapter that connects the recycler view and the photo view holder.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {

    /*
    Context that will hold context from the main activity.
     */
    private Context context;
    /*
    List of photo taken by user.
     */
    private final ArrayList<Bitmap> photoList;
    /*
    Inflater used to inflate the image view holding the photos.
     */
    private LayoutInflater inflater;

    /*
    Constructor that instantiates all the above variables.
     */
    public PhotoListAdapter(Context context, ArrayList<Bitmap> photoList){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.photoList = photoList;
    }

    /*
    Inflates the imageview and creates a photoViewHolder by passing the View, the adapter, the context and the photolist.
     */
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.photolist_item, parent, false);
        return new PhotoViewHolder(itemView, this, this.context, this.photoList);
    }

    /*
    Called when a new ImageView is selected from the recycler view.
     */
    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Bitmap current = this.photoList.get(position);
        holder.imageItemView.setImageBitmap(current);
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return this.photoList.size();
    }

    /*
    ViewHolder that handles the behaviour of individual photos.
     */
    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /*
        Context of the main activity.
         */
        private Context context;
        /*
        ImageView holding the photo is selected from the recycler view.
         */
        private final ImageView imageItemView;
        /*
        List of photos
         */
        private ArrayList<Bitmap> photoList;
        /*
        Position of the photo in the list.
         */
        private int position;
        /*
        Adapter that connects this object with the recycler view.
         */
        final PhotoListAdapter adapter;

        /*
        Constructor that instantiates all the above variables.
         */
        public PhotoViewHolder(View itemView, PhotoListAdapter adapter, Context context, ArrayList<Bitmap> photoList) {
            super(itemView);
            this.imageItemView = itemView.findViewById(R.id.photo);
            this.adapter = adapter;
            this.context = context;
            this.photoList = photoList;
            this.position = 0;
            itemView.setOnClickListener(this);
        }

        /*
        Called when a photo is clicked by the user.
        Starts a child activity that displays the selected photo.
        The list and the position are added to the intent.
         */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(this.context, FullScreenPhoto.class);
            intent.putExtra("EXTRA_LIST", this.photoList);
            intent.putExtra("EXTRA_POSITION", this.position);
            ((MainActivity)this.context).startActivityForResult(intent, 1);
        }
    }
}
