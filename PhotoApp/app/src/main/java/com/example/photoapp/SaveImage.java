package com.example.photoapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
Thread used to add an image to the internal storage.
 */
public class SaveImage extends AsyncTask {

    /*
    Context of the main acitivity.
     */
    private Context context;
    /*
    The photo as a bitmap that needs to be stored.
     */
    private Bitmap image;
    /*
    Position of the photo in list.
     */
    private int position;

    /*
    Constructor that instantiates the above variables.
     */
    public SaveImage(Context context, Bitmap image, int position){
        this.context = context;
        this.image = image;
        this.position = position;
    }

    /*
    Work done by the thread.
    Accesses the root of the internal storage -> get the "images" directory.
    Creates a new file that will hold the photo.
    Use a file output stream to compress the bitmap to the file.
     */
    @Override
    protected Object doInBackground(Object[] objects) {
        File root = this.context.getFilesDir();
        File dir = new File(root.getAbsolutePath(), "/images");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "ImageName" + this.position + ".jpg");
        try {
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
