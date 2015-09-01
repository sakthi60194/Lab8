package com.android.sakthi.cameraopen;

import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SAKTHI on 9/1/2015.
 */
public class PhotoHandler implements Camera.PictureCallback {

    private final Context context;
    ProgressDialog progressDialog;
    public PhotoHandler(Context applicationContext) {
        this.context = applicationContext;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Saving in device");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {


        File pictureFile = getOutputMediaFile();

        if (pictureFile == null) {
            Toast.makeText(context, "NULL", Toast.LENGTH_LONG).show();
            progressDialog.cancel();
            return;
        }
        try {
            //write the file
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            progressDialog.cancel();
            Toast.makeText(context, "Picture saved: " + pictureFile.getName(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
        }

    }

    private File getOutputMediaFile() {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"Sakthi");

       
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            Toast.makeText(context,mediaStorageDir.getAbsolutePath(),Toast.LENGTH_SHORT).show();
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        //and make a media file:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }
}
