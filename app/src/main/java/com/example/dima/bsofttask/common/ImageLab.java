package com.example.dima.bsofttask.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageLab implements Serializable {

    private Context context;
    private String currentPhotoPath;
    private File file;
    private String timeStamp;
    private String stringBase64Binary;
    private boolean isCodeBase64 = false;

    public Context getContext() {
        return context;
    }

    public boolean isCodeBase64() {
        return isCodeBase64;
    }

    public String getStringBase64Binary() {
        return stringBase64Binary;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ImageLab(Context context) {
        this.context = context;
        this.file = createImageFile();
    }

    private File createImageFile() {
        // Create an image file name
        timeStamp = new SimpleDateFormat("E dd.MM.yyyy"
                , new Locale("en", "US"))
                .format(new Date());

        String imageFileName = timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error", e.getMessage());
        }

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void encodeToStringBase64() {
        Bitmap bm = PictureUtils.getScaledBitmap(file.getAbsolutePath(),800,600); // if not scaled, server return error
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        isCodeBase64 = true;
        stringBase64Binary = Base64.encodeToString(b, Base64.DEFAULT);
    }

    public String encodeImage(Activity activity) {
        Bitmap bm = getScaledBitmap(file.getAbsolutePath(),activity);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        stringBase64Binary = encImage;
        return encImage;
    }



    private  Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        //        Reading images on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(path, options);
    }

    private  Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }
    public void galleryAddPic() { // add to gallery
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }
}
