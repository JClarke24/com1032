package com.example.photoapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    /*
    List of bitmap that will hold all the photo's taken.
     */
    private ArrayList<Bitmap> photoList = new ArrayList<>();
    /*
    Recycler view used to display the list of imageviews
     */
    private RecyclerView recyclerView;
    /*
    Adapter used by recycler to add an imageview.
     */
    private PhotoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            /*
            The camera app is started and the photo taken will be returned in onActivityResult() method.
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        /*
        Calls the methods that accesses the previously stored photo's in the device.
         */
        this.photoList = loadImages();

        this.recyclerView = findViewById(R.id.recyclerview);
        /*
        Depending on the orientation of the device, the columns of the recycler view will be arranged differently.
         */
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        }
        else{
            this.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        this.adapter = new PhotoListAdapter(this, this.photoList);
        this.recyclerView.setAdapter(this.adapter);

    }


    /*
    Method that handles the return intent from the camera app which holds the photo taken. (request code 0)
    Also handles the intent passed from the child activity when a photo is deleted. (request code 1)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0) {
            Bitmap image = (Bitmap) data.getExtras().get("data");

            saveImage(image);

            this.photoList.add(image);
            this.recyclerView.getAdapter().notifyItemInserted(this.photoList.size());
            this.recyclerView.smoothScrollToPosition(this.photoList.size());
        }
        else if(requestCode == 1){
            int position = (Integer) data.getExtras().get("EXTRA_POSITION");

            removeImage(position);

            this.photoList.remove(position);
            this.recyclerView.getAdapter().notifyItemRemoved(position);
        }
    }

    /*
    Creates a new thread that will access the apps internal storage and add files containing the image taken by the user.
     */
    public void saveImage(Bitmap image){
        new SaveImage(this, image, this.photoList.size()).execute();
    }

    /*
    Creates a new thread that will access the apps internal storage and deleted the file containing the image chosen by the user.
     */
    public void removeImage(int position){
        new RemoveImage(this, position).execute();
    }

    /*
    Called in the onCreate() method, used to create a list and fill it up with the images contained in the files in internal storage.
     */
    public ArrayList<Bitmap> loadImages(){
        ArrayList<Bitmap> tempList = new ArrayList<>();

        File root = getFilesDir();
        File dir = new File(root.getAbsolutePath(), "/images");
        File[] fileList = dir.listFiles();
        if(fileList != null) {
            for (File file : fileList) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                if (bitmap != null) {
                    tempList.add(bitmap);
                }
            }
        }

        return tempList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
