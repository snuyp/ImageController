package com.example.dima.bsofttask.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.dima.bsofttask.common.ImageLab;
import com.example.dima.bsofttask.mvp.view.PhotoView;
import com.example.dima.bsofttask.ui.fragment.PhotoViewFragment;

import java.io.File;

@InjectViewState
public class PhotoPresenter extends MvpPresenter<PhotoView> {
    private ImageLab imageLab;
    public void toTakePhoto(Context context) {
        imageLab = new ImageLab(context);
        dispatchTakePictureIntent(context, imageLab.getFile());
    }

    private void dispatchTakePictureIntent(Context context, File file) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            // Continue only if the File was successfully created
            Uri uri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider", file);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            getViewState().takePicture(takePictureIntent);
        }
    }

    public void updatePhotoView() {
        if (imageLab.getFile() == null || !imageLab.getFile().exists()) {
            Log.e("Error", "File is null");
        } else {
            getViewState().showPhoto(PhotoViewFragment.newInstance(imageLab));
        }
    }
}
