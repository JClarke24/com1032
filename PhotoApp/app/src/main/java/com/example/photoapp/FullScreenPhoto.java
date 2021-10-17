package com.example.photoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/*
Child activity used to display the selected photo.
 */
public class FullScreenPhoto extends AppCompatActivity {

    /*
    List of photo.
     */
    private ArrayList<Bitmap> photoList;
    /*
    Position of photo in list.
     */
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);

        ImageView fullScreen = findViewById(R.id.full_screen);

        /*
        Get the data from the intent.
        Display the photo on the layout.
         */
        Intent intent = getIntent();
        this.photoList = intent.getParcelableArrayListExtra("EXTRA_LIST");
        this.position = intent.getIntExtra("EXTRA_POSITION", -1);

        if(this.position >= 0) {
            Bitmap photo = this.photoList.get(this.position);
            fullScreen.setImageBitmap(photo);
        }
    }

    /*
    Called when the delete button is clicked.
    Returns the position of the photo so that it is deleted in the main activity.
     */
    public void onClickButton(View view){
        Intent data = new Intent();
        data.putExtra("EXTRA_POSITION", this.position);
        setResult(RESULT_OK, data);
        finish();
    }

}
