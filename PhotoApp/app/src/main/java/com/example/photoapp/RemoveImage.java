package com.example.photoapp;

import android.content.Context;
import android.os.AsyncTask;
import java.io.File;

/*
Thread used to remove an image from the internal storage.
 */
public class RemoveImage extends AsyncTask {

    /*
    Context of the main activity.
     */
    private Context context;
    /*
    Position of image to be removed.
     */
    private int position;

    /*
    Constructor that instantiates the above variables.
     */
    public RemoveImage(Context context, int position){
        this.context = context;
        this.position = position;
    }

    /*
    Work done by the thread.
    Accesses the root of the internal storage -> get the "images" directory -> get an array of all files in the directory.
    Find the corresponding file and delete it.
     */
    @Override
    protected Object doInBackground(Object[] objects) {

        File root = this.context.getFilesDir();
        File dir = new File(root.getAbsolutePath(), "/images");
        File[] fileList = dir.listFiles();

        if(fileList != null && fileList.length > this.position) {
            File file = fileList[this.position];
            if(file != null && file.exists()){
                file.delete();
            }
            else{
                System.out.println("File not found");
            }
        }
        else{
            System.out.println("Directory is empty");
        }

        return null;
    }
}
